package com.bbn.hadder.commands.owner;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.commands.general.HelpCommand;
import com.bbn.hadder.utils.MessageEditor.MessageType;

/*
 * @author Skidder / GregTCLTK
 */

public class TestCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageType.INFO, "commands.owner.test.success", "").build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"test"};
    }

    @Override
    public String description() {
        return "commands.owner.test.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
