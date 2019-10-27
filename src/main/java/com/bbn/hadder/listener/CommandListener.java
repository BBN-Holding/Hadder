package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.core.CommandParser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/*
 * @author Skidder / GregTCLTK
 */

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getMessage().getContentRaw().startsWith(Rethink.get("server", "id", event.getGuild().getId(), "prefix"))) {
                if (!event.getAuthor().isBot()) {
                    CommandHandler.handleCommand(CommandParser.parser(event.getMessage().getContentRaw(), event));
                } else if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_ADD_REACTION)) {
                    event.getMessage().addReaction("🙅").queue();
                }
            }
        }
    }
}
