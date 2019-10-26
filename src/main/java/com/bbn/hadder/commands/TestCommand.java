package com.bbn.hadder.commands;

/*
 * @author Skidder / GregTCLTK
 */

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommand implements Command {

    public void executed(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("TEST my friends").queue();
    }
}
