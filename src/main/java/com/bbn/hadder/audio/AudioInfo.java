package com.bbn.hadder.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Member;

/**
 * @author Skidder / GregTCLTK
 */

public class AudioInfo {

    private final AudioTrack track;
    private final Member author;

    AudioInfo(AudioTrack track, Member author) {
        this.track = track;
        this.author = author;
    }

    public AudioTrack getTrack() {
        return track;
    }

    public Member getAuthor() {
        return author;
    }

}
