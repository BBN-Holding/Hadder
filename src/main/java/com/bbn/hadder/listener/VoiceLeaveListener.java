package com.bbn.hadder.listener;

import com.bbn.hadder.audio.AudioManager;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Skidder / GregTCLTK
 */

public class VoiceLeaveListener extends ListenerAdapter {

    private AudioManager audioManager;

    public VoiceLeaveListener(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
        if (audioManager.hasPlayer(e.getGuild()) && e.getChannelLeft().getMembers().equals(e.getGuild().getSelfMember())) {
            audioManager.players.remove(e.getGuild().getId());
            audioManager.getPlayer(e.getGuild()).destroy();
            audioManager.getTrackManager(e.getGuild()).purgeQueue();
            e.getGuild().getAudioManager().closeAudioConnection();
        }
    }
}
