package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.audio.AudioManager;
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
    private AudioManager audioManager;

    public CommandListener(Rethink rethink, CommandHandler handler, AudioManager audioManager) {
        this.rethink = rethink;
        this.handler = handler;
        this.audioManager = audioManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromType(ChannelType.TEXT) && !e.getAuthor().isBot()) {
            String[] prefixes = {
                    rethink.getUserPrefix(e.getAuthor().getId()), rethink.getGuildPrefix(e.getGuild().getId()),
                    e.getGuild().getSelfMember().getAsMention() + " ", e.getGuild().getSelfMember().getAsMention(),
                    e.getGuild().getSelfMember().getAsMention().replace("@", "@!") + " ",
                    e.getGuild().getSelfMember().getAsMention().replace("@", "@!")
            };
            for (String prefix : prefixes) {
                if (e.getMessage().getContentRaw().startsWith(prefix)) {
                    handler.handle(e, rethink, prefix, audioManager);
                    return;
                }
            }
        }
    }
}
