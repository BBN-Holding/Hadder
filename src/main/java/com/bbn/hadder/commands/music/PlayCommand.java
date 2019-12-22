package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Message;

import java.net.URL;

/**
 * @author Skidder / GregTCLTK
 */

public class PlayCommand implements Command {

    /*
    private static final String CD = "\uD83D\uDCBF";
    private static final String MIC = "\uD83C\uDFA4 **|>** "; */


    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getMember().getVoiceState().inVoiceChannel()) {
                String input = event.getMessage().getContentRaw().replaceFirst(event.getRethink().getGuildPrefix(event.getGuild().getId()) + "play ", "").replaceFirst(event.getRethink().getUserPrefix(event.getAuthor().getId()) + "play ", "");
                try {
                    new URL(input).toURI();
                    Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.play.load.title", "⭕",
                            "commands.music.play.load.description", "").build()).complete();
                    new AudioManager().loadTrack(input, event, msg);
                } catch (Exception ignore) {
                    Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.play.load.title", "⭕",
                            "commands.music.play.load.description", "").build()).complete();
                    new AudioManager().loadTrack("ytsearch: " + input, event, msg);
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.WARNING,
                        "commands.music.join.error.channel.title",
                        "commands.music.join.error.channel.description")
                        .build()).queue();
            }
        } else event.getHelpCommand().sendHelp(this, event);


        /* OUTSOURCE THIS
        Guild guild = event.getGuild();
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "info":
                    if (!hasPlayer(guild) || getPlayer(guild).getPlayingTrack() == null) { // No song is playing
                        event.getTextChannel().sendMessage("No song is being played at the moment! *It's your time to shine..*").queue();
                    } else {
                        AudioTrack track = getPlayer(guild).getPlayingTrack();
                        event.getTextChannel().sendMessage("Track Info" + String.format(QUEUE_DESCRIPTION, CD, getOrNull(track.getInfo().title),
                                "\n\u23F1 **|>** `[ " + getTimestamp(track.getPosition()) + " / " + getTimestamp(track.getInfo().length) + " ]`",
                                "\n" + MIC, getOrNull(track.getInfo().author),
                                "\n\uD83C\uDFA7 **|>**  " + "")).queue();
                    }
                    break;

                case "queue":
                    if (!hasPlayer(guild) || getTrackManager(guild).getQueuedTracks().isEmpty()) {
                        event.getTextChannel().sendMessage("The queue is empty! Load a song with **"
                                + "dd" + "music play**!").queue();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        Set<AudioInfo> queue = getTrackManager(guild).getQueuedTracks();
                        queue.forEach(audioInfo -> sb.append(buildQueueMessage(audioInfo)));
                    }
                    break;
            }
        } */

    }

    @Override
    public String[] labels() {
        return new String[]{"play"};
    }

    @Override
    public String description() {
        return "commands.music.play.help.description";
    }

    @Override
    public String usage() {
        return "song";
    }
}
