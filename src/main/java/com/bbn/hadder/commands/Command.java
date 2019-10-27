package com.bbn.hadder.commands;

/*
 * @author Skidder / GregTCLTK
 */

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    void executed(String[] args, MessageReceivedEvent event);
}
