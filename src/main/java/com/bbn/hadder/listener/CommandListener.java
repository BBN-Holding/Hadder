package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.CommandHandler;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/*
 * @author Skidder / GregTCLTK
 */

public class CommandListener extends ListenerAdapter {

    private Rethink rethink;
    private CommandHandler handler;

    public CommandListener(Rethink rethink, CommandHandler handler) {
        this.rethink = rethink;
        this.handler = handler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (!event.getAuthor().isBot()) {
                String[] prefixes = {rethink.getUserPrefix(event.getAuthor().getId()), rethink.getServerPrefix(event.getGuild().getId()), event.getGuild().getSelfMember().getAsMention()+" ", event.getGuild().getSelfMember().getAsMention()};
                for (String prefix : prefixes) {
                    if (event.getMessage().getContentRaw().startsWith(prefix)) {
                        handler.handle(event, rethink, prefix);
                        return;
                    }
                }
            }
        }
    }
}
