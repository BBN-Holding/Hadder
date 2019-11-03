package com.bbn.hadder;

import com.bbn.hadder.commands.general.*;
import com.bbn.hadder.commands.fun.GifCommand;
import com.bbn.hadder.commands.misc.EqualsCommand;
import com.bbn.hadder.commands.misc.GitHubCommand;
import com.bbn.hadder.commands.misc.ScreenshareCommand;
import com.bbn.hadder.commands.moderation.*;
import com.bbn.hadder.commands.owner.RebootCommand;
import com.bbn.hadder.commands.owner.ShutdownCommand;
import com.bbn.hadder.commands.owner.TestCommand;
import com.bbn.hadder.commands.settings.PrefixCommand;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.core.Config;
import com.bbn.hadder.listener.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.List;

public class Hadder {

    public static void main(String[] args) {
        startBot();
    }

    public static void startBot() {
        Config config = new Config("./config.json");
        if (!config.fileExists()) config.create();
        config.load();

        Rethink rethink = new Rethink(config);
        rethink.connect();

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();

        builder.setShardsTotal(1);
        builder.setActivity(Activity.streaming("on the BigBotNetwork", "https://twitch.tv/BigBotNetwork"));
        builder.setToken(config.getToken());


        CommandHandler commandHandler = new CommandHandler(
                List.of(
                        new HelpCommand(),
                        new TestCommand(),
                        new BanCommand(),
                        new PrefixCommand(),
                        new ShutdownCommand(),
                        new KickCommand(),
                        new PingCommand(),
                        new GifCommand(),
                        new ClearCommand(),
                        new GitHubCommand(),
                        new ScreenshareCommand(),
                        new RebootCommand(),
                        new EqualsCommand()), config);
       
        builder.addEventListeners(
                new MentionListener(),
                new PrivateMessageListener(),
                new CommandListener(rethink, commandHandler),
                new GuildListener(rethink),
                new ReadyListener(rethink));

        try {
            ShardManager shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
