package com.bbn.hadder.audio;

import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Skidder / GregTCLTK
 */

public class AudioManager {

    public static final Map<String, Map.Entry<AudioPlayer, TrackManager>> players = new HashMap<>();
    public static final AudioPlayerManager myManager = new DefaultAudioPlayerManager();

    public static void loadTrack(String identifier, CommandEvent event, Message msg) {

        Guild guild = event.getGuild();
        AudioManager.getPlayer(guild);

        myManager.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                AudioManager.getTrackManager(guild).queue(track, event.getMember());
                msg.editMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.music.play.success.loading.title", "⏯",
                        "", "")
                        .addField(event.getMessageEditor().getTerm("commands.music.play.success.title"), track.getInfo().title, false)
                        .addField(event.getMessageEditor().getTerm("commands.music.play.success.author"), track.getInfo().author, true)
                        .addField(event.getMessageEditor().getTerm("commands.music.play.success.length"),
                                String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(track.getInfo().length),
                                        TimeUnit.MILLISECONDS.toMinutes(track.getInfo().length) % TimeUnit.HOURS.toMinutes(1),
                                        TimeUnit.MILLISECONDS.toSeconds(track.getInfo().length) % TimeUnit.MINUTES.toSeconds(1)), true)
                        .build()).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.getSelectedTrack() != null) {
                    trackLoaded(playlist.getSelectedTrack());
                } else if (playlist.isSearchResult()) {
                    trackLoaded(playlist.getTracks().get(0));
                } else {
                    for (int i = 0; i < Math.min(playlist.getTracks().size(), 200); i++) {
                        AudioManager.getTrackManager(guild).queue(playlist.getTracks().get(i), event.getMember());
                    }
                }
            }

            @Override
            public void noMatches() {
                msg.editMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.music.play.error.match.title", "❌",
                        "commands.music.play.error.match.description", "")
                        .build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                msg.editMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.music.play.error.load.title", "❌",
                        "commands.music.play.error.load.description", "")
                        .build()).queue();
            }
        });
    }

    public static boolean hasPlayer(Guild guild) {
        return players.containsKey(guild.getId());
    }

    public static AudioPlayer getPlayer(Guild guild) {
        AudioPlayer p;
        if (hasPlayer(guild)) {
            p = players.get(guild.getId()).getKey();
        } else {
            p = createPlayer(guild);
        }
        return p;
    }

    public static TrackManager getTrackManager(Guild guild) {
        return players.get(guild.getId()).getValue();
    }

    public static AudioPlayer createPlayer(Guild guild) {
        AudioPlayer nPlayer = myManager.createPlayer();
        TrackManager manager = new TrackManager(nPlayer);
        nPlayer.addListener(manager);
        guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(nPlayer));
        players.put(guild.getId(), new AbstractMap.SimpleEntry<>(nPlayer, manager));
        return nPlayer;
    }

}
