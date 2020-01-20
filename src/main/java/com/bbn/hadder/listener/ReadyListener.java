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
    public void onReady(@Nonnull ReadyEvent e) {
        rethink.setup();
        new Thread(() -> {
            for (User user : e.getJDA().getUsers()) {
                if (!user.getId().equals(e.getJDA().getSelfUser().getId())) {
                    rethink.insertUser(user.getId());
                }
            }
            for (Guild g : e.getJDA().getGuilds()) {
                rethink.insertGuild(g.getId());
            }
        }).start();

        new BotList(config).post();
    }
}
