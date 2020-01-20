package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class SkipCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getAudioManager().hasPlayer(e.getGuild()) && !e.getAudioManager().getTrackManager(e.getGuild()).getQueuedTracks().isEmpty()) {
            if (e.getMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())) {
                e.getAudioManager().forceSkipTrack(e);
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.music.skip.success.title",
                        "commands.music.skip.success.description").build()).queue();
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.skip.error.connected.title",
                        "commands.music.skip.error.connected.description ").build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.info.error.title",
                    "commands.music.info.error.description").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"skip"};
    }

    @Override
    public String description() {
        return "commands.music.skip.help.description";
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
