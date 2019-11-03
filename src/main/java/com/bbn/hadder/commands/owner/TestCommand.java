package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
        return "Sub to bbn";
    }

    @Override
    public String usage() {
        return "";
    }
}
