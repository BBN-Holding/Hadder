package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class LinkListener extends ListenerAdapter {

    Rethink rethink;

    public LinkListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        // TODO
    }
}
