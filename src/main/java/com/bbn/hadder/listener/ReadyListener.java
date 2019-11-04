package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.Config;
import com.bbn.hadder.utils.BotList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    private Rethink rethink;
    private Config config;

    public ReadyListener(Rethink rethink, Config config) {
        this.rethink = rethink;
        this.config = config;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        rethink.setup();
        for (User user : event.getJDA().getUsers()) {
            if (!user.getId().equals(event.getJDA().getSelfUser().getId())) {
                rethink.insertUser(user.getId());
            }
        }
        for (Guild g : event.getJDA().getGuilds()) {
            rethink.insertServer(g.getId());
        }

        new BotList(config).post();
    }
}
