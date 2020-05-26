/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.listener;

import com.bbn.hadder.core.Config;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class OwnerMessageListener extends ListenerAdapter {

    Config config;

    public OwnerMessageListener(Config config) {
        this.config = config;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (config.getOwners().contains(event.getAuthor().getIdLong()) && event.getMessage().getContentRaw().startsWith(":") && event.getMessage().getContentRaw().endsWith(":")) {
            String emotename = event.getMessage().getContentRaw().split(":")[1];
            if (!emotename.contains(" ")) {
                Emote[] emotes = event.getJDA().getEmotesByName(emotename, true).toArray(new Emote[0]);
                StringBuilder sb = new StringBuilder();
                if (emotes.length!=0) {
                    for (Emote emote : emotes) {
                        sb.append(emote.getAsMention()).append(" ");
                    }
                    event.getChannel().sendMessage(sb.toString()).queue();
                }
            }
        }
    }
}
