package com.bbn.hadder.commands.music;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class LeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            if (e.getMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())) {
                e.getGuild().getAudioManager().closeAudioConnection();
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.music.leave.success.title",
                        "commands.music.leave.success.description")
                        .build()).queue();
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.leave.error.channel.title",
                        "commands.music.leave.error.channel.description")
                        .build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                MessageEditor.MessageType.ERROR,
                "commands.music.leave.error.connected.tile",
                "commands.music.leave.error.connected.description")
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"leave", "quit"};
    }

    @Override
    public String description() {
        return "commands.music.leave.help.description";
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
