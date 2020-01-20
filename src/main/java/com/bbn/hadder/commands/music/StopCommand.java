package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class StopCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getAudioManager().hasPlayer(e.getGuild()) && e.getAudioManager().getPlayer(e.getGuild()).getPlayingTrack() != null) {
            if (e.getMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())) {
                e.getAudioManager().players.remove(e.getGuild().getId());
                e.getAudioManager().getPlayer(e.getGuild()).destroy();
                e.getAudioManager().getTrackManager(e.getGuild()).purgeQueue();
                e.getGuild().getAudioManager().closeAudioConnection();
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.music.stop.success.title",
                        "commands.music.stop.success.description").build()).queue();
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.stop.error.connected.title",
                        "commands.music.stop.error.connected.description")
                        .build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.info.error.title",
                    "commands.music.info.error.description").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"stop"};
    }

    @Override
    public String description() {
        return "commands.music.stop.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
