package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class SkipCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (!event.getAudioManager().hasPlayer(event.getGuild()) || event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack() == null) {
            event.getAudioManager().forceSkipTrack(event);
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.skip.success.title",
                    "commands.music.skip.success.description").build()).queue();
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
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
        return "";
    }
}
