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
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (new AudioManager().hasPlayer(event.getGuild()) && event.getChannelLeft().getMembers().equals(event.getGuild().getSelfMember())) {
            audioManager.players.remove(event.getGuild().getId());
            audioManager.getPlayer(event.getGuild()).destroy();
            audioManager.getTrackManager(event.getGuild()).purgeQueue();
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }
}
