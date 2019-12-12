package com.bbn.hadder.commands.music;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class LeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.music.leave.success.title"))
                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.music.leave.success.description"))
                    .build()).queue();
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.music.leave.error.tile"))
                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.music.leave.error.description"))
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"leave"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.music.leave.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
