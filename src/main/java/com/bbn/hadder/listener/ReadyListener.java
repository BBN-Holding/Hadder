package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        Rethink.setup();
        for (Guild g : event.getJDA().getGuilds()) {
            Rethink.insertServer(g.getId());
        }
    }
}
