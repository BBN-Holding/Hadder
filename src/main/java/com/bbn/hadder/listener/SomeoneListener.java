/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.listener;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Random;

public class SomeoneListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("@someone")) {
            int member = new Random().nextInt(event.getGuild().getMembers().size()-1);
            if (member>0&&member<event.getGuild().getMembers().size()) {
                event.getChannel().sendMessage(event.getGuild().getMembers().get(member).getAsMention()+ " (Executed by: "+event.getAuthor().getAsTag()+")").queue();
            }
        }
    }
}
