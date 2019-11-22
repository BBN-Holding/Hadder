package com.bbn.hadder.commands.owner;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

/*
 * @author Skidder / GregTCLTK
 */

public class TestCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getTextChannel().sendMessage("TEST my friends").queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"test"};
    }

    @Override
    public String description() {
        return "Just a little Test Command";
    }

    @Override
    public String usage() {
        return "";
    }
}
