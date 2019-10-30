package com.bbn.hadder.commands.general;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/*
 * @author Skidder / GregTCLTK
 */

public class TestCommand implements Command {

    public void executed(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("TEST my friends").queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"test"};
    }
}
