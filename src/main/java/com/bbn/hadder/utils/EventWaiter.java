package com.bbn.hadder.utils;

import com.bbn.hadder.commands.CommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EventWaiter {

    public void newOnMessageEventWaiter(Consumer<GuildMessageReceivedEvent> onEvent, CommandEvent event) {
        Object listener = new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (event.getAuthor() == null) {
                    onEvent.accept(event);
                    event.getJDA().getShardManager().removeEventListener(this);
                } else if (event.getAuthor().getId().equals(event.getAuthor().getId())) {
                    onEvent.accept(event);
                    event.getJDA().getShardManager().removeEventListener(this);
                }
            }
        };
        event.getJDA().getShardManager().addEventListener(listener);
    }

}
