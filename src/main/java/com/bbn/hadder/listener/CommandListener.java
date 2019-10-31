package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.core.CommandParser;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/*
 * @author Skidder / GregTCLTK
 */

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (!event.getAuthor().isBot()) {
                if (event.getMessage().getContentRaw().startsWith(Rethink.get("user", "id", event.getAuthor().getId(), "prefix"))) {
                    CommandHandler.handleCommand(CommandParser.parser(event.getMessage().getContentRaw(), event));
                }
            }
        }
    }
}
