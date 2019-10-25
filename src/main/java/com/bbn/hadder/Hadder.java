package com.bbn.hadder;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hadder {

    public static void main(String[] args) {

        File jsonfile = new File("./config.json");
        if (!jsonfile.exists()) {
            System.err.println("No Config File Found!");
            System.exit(1);
        }

        JSONObject config = null;
        try {
            config = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonfile.toURI()))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();

        builder.setShardsTotal(1);
        builder.setActivity(Activity.streaming("auf dem BigBotNetwork", "https://twitch.tv/BigBotNetwork"));
        builder.setToken(config.getString("Token"));

        try {
            ShardManager shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }
    
}
