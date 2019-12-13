package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;


public class RebootCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            Runtime.getRuntime().exit(69);
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"restart", "restart"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.owner.reboot.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
