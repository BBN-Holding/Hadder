package com.bbn.hadder.listener;

import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.audio.TrackManager;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Skidder / GregTCLTK
 */

public class VoiceLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (new AudioManager().hasPlayer(event.getGuild())) {
            TrackManager manager = new AudioManager().getTrackManager(event.getGuild());
            manager.getQueuedTracks().stream()
                    .filter(info -> !info.getTrack().equals(new AudioManager().getPlayer(event.getGuild()).getPlayingTrack())
                            && info.getAuthor().getUser().equals(event.getMember().getUser()))
                    .forEach(manager::remove);
        }
    }
}
