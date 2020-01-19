package com.bbn.hadder.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Skidder / GregTCLTK
 */

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer player;
    private final AudioManager manager;
    private final Queue<AudioInfo> queue;
    AudioTrack lastTrack;
    private boolean loop = false;

    public TrackManager(AudioPlayer player, AudioManager manager) {
        this.manager = manager;
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);

        if (player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();
        VoiceChannel vChan = info.getAuthor().getVoiceState().getChannel();
        if (vChan == null) {
            player.stopTrack();
        } else {
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChan);
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild g = queue.poll().getAuthor().getGuild();
        this.lastTrack = track;
        if (loop) {
            player.playTrack(lastTrack.makeClone());
        } else if (queue.isEmpty()) {
            manager.players.remove(g.getId());
            manager.getPlayer(g).destroy();
            manager.getTrackManager(g).purgeQueue();
            g.getAudioManager().closeAudioConnection();
        } else {
            player.playTrack(queue.element().getTrack());
        }
    }

    public Set<AudioInfo> getQueuedTracks() {
        return new LinkedHashSet<>(queue);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void remove(AudioInfo entry) {
        queue.remove(entry);
    }

    public boolean isLoop()
    {
        return loop;
    }

    public void setLoop(boolean repeating)
    {
        this.loop = repeating;
    }

    public AudioInfo getTrackInfo(AudioTrack track) {
        return queue.stream().filter(audioInfo -> audioInfo.getTrack().equals(track)).findFirst().orElse(null);
    }
}
