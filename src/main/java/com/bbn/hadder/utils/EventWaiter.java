package com.bbn.hadder.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EventWaiter {

    public void newOnMessageEventWaiter(Consumer<GuildMessageReceivedEvent> onEvent, JDA jda, User user) {
        Object listener = new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (user==null) {
                    onEvent.accept(event);
                    event.getJDA().getShardManager().removeEventListener(this);
                } else if (event.getAuthor().getId().equals(user.getId())) {
                    onEvent.accept(event);
                    event.getJDA().getShardManager().removeEventListener(this);
                }
            }
        };
        jda.getShardManager().addEventListener(listener);
    }

}
