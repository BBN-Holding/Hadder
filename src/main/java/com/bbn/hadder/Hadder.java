package com.bbn.hadder;

import com.bbn.hadder.commands.general.*;
import com.bbn.hadder.commands.fun.GifCommand;
import com.bbn.hadder.commands.misc.GitHubCommand;
import com.bbn.hadder.commands.moderation.*;
import com.bbn.hadder.commands.owner.ShutdownCommand;
import com.bbn.hadder.commands.settings.PrefixCommand;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.listener.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Hadder {

    public static void main(String[] args) {

        File configfile = new File("./config.json");
        if (!configfile.exists()) {
            System.err.println("No Config File Found!");
            System.exit(1);
        }

        JSONObject config = null;
        try {
            config = new JSONObject(new String(Files.readAllBytes(Paths.get(configfile.toURI()))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rethink.connect();

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();

        builder.setShardsTotal(1);
        builder.setActivity(Activity.streaming("auf dem BigBotNetwork", "https://twitch.tv/BigBotNetwork"));
        builder.setToken(config.getString("Token"));


        CommandHandler.cmdlist.addAll(List.of(new TestCommand(), new BanCommand(), new PrefixCommand(), new ShutdownCommand(), new KickCommand(), new PingCommand(), new GifCommand(), new ClearCommand(), new GitHubCommand()));

        builder.addEventListeners(
                new MentionListener(),
                new PrivateMessageListener(),
                new CommandListener(),
                new GuildListener(),
                new ReadyListener());

        try {
            ShardManager shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }
    
}
