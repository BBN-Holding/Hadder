package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class StopCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getAudioManager().hasPlayer(event.getGuild())) {
            event.getAudioManager().players.remove(event.getGuild().getId());
            event.getAudioManager().getPlayer(event.getGuild()).destroy();
            event.getAudioManager().getTrackManager(event.getGuild()).purgeQueue();
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.stop.success.title",
                    "commands.music.stop.success.description").build()).queue();
        } else {
            event.getTextChannel().sendMessage("I love you <3").queue();
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
        return "";
    }
}
