package com.bbn.hadder.utils;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Hadder;
import com.bbn.hadder.core.Config;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotList {

    private static String MythicalBotList = "https://mythicalbots.xyz/api/bot/637002314162372639";
    private static String BotsForDiscord = "https://botsfordiscord.com/api/bot/637002314162372639";
    private static String DiscordBotList = "https://discordbotlist.com/api/bots/637002314162372639/stats";
    private static String DiscordBoats = "https://discord.boats/api/bot/637002314162372639";
    private static String YetAnotherBotList = "https://yabl.xyz/api/bot/637002314162372639/stats";
    private static String DiscordExtremeList = "https://api.discordextremelist.xyz/v1/bot/637002314162372639";
    private static String DiscordBotReviews = "https://discordbotreviews.xyz/api/bot/637002314162372639/stats";
    private static String DiscordBots = "https://top.gg/api/bots/637002314162372639/stats";
    private static String BotListSpace = "https://api.botlist.space/v1/bots/637002314162372639";
    private static String DiscordBots2 = "https://discord.bots.gg/api/v1/bots/637002314162372639/stats";
    private static String CloudList = "https://www.cloudlist.xyz/api/stats/637002314162372639";

    private Config config;

    public BotList(Config config) {
        this.config = config;
    }

    public void post() {
        if (Files.notExists(Paths.get("./DEBUG"))) {
            JSONObject json = new JSONObject();
            json.put("server_count", Hadder.shardManager.getGuilds().size());
            json.put("guildCount", Hadder.shardManager.getGuilds().size());
            json.put("guilds", Hadder.shardManager.getGuilds().size());
            json.put("count", Hadder.shardManager.getGuilds().size());
            json.put("users", Hadder.shardManager.getUsers().size());
            json.put("shard_count", Hadder.shardManager.getShards().size());
            json.put("shardCount", Hadder.shardManager.getShards().size());

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

            // Mythical Bot List

            Request mythicalbotlist = new Request.Builder()
                    .url(MythicalBotList)
                    .post(body)
                    .addHeader("Authorization", config.getMythicalBotListToken())
                    .build();

            try {
                new OkHttpClient().newCall(mythicalbotlist).execute().close();
                System.out.println("Successfully posted count for the Mythical Bot List!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // BotsForDiscord

            Request botsfordiscord = new Request.Builder()
                    .url(BotsForDiscord)
                    .post(body)
                    .addHeader("Authorization", config.getBotsForDiscordToken())
                    .build();

            try {
                new OkHttpClient().newCall(botsfordiscord).execute().close();
                System.out.println("Successfully posted count to Bots For Discord!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Discord Bot List

            Request discordbotlist = new Request.Builder()
                    .url(DiscordBotList)
                    .post(body)
                    .addHeader("Authorization", "Bot " + config.getDiscordBotListToken())
                    .build();

            try {
                new OkHttpClient().newCall(discordbotlist).execute().close();
                System.out.println("Successfully posted count for the Discord Bot List!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Discord Boats

            Request discordboats = new Request.Builder()
                    .url(DiscordBoats)
                    .post(body)
                    .addHeader("Authorization", config.getDiscordBoatsToken())
                    .build();

            try {
                new OkHttpClient().newCall(discordboats).execute().close();
                System.out.println("Successfully posted count to Discord Boats!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Yet Another Bot List

            Request yetanotherbotlist = new Request.Builder()
                    .url(YetAnotherBotList)
                    .post(body)
                    .addHeader("Authorization", config.getYetAnotherBotListToken())
                    .build();

            try {
                new OkHttpClient().newCall(yetanotherbotlist).execute().close();
                System.out.println("Successfully posted count to Yet Another Bot List!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Discord Extreme List

            Request discordextremelist = new Request.Builder()
                    .url(DiscordExtremeList)
                    .post(body)
                    .addHeader("Authorization", config.getDiscordExtremeListToken())
                    .build();

            try {
                new OkHttpClient().newCall(discordextremelist).execute().close();
                System.out.println("Successfully posted count for the Discord Extreme List!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Discord Bot Reviews

            Request discordbotreviews = new Request.Builder()
                    .url(DiscordBotReviews)
                    .post(body)
                    .addHeader("Authorization", config.getDiscordBotReviewsToken())
                    .build();

            try {
                new OkHttpClient().newCall(discordbotreviews).execute().close();
                System.out.println("Successfully posted count to Discord Bot Reviews!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Discord Bots

            Request discordbots = new Request.Builder()
                    .url(DiscordBots)
                    .post(body)
                    .addHeader("Authorization", config.getDiscordBotsToken())
                    .build();

            try {
                new OkHttpClient().newCall(discordbots).execute().close();
                System.out.println("Successfully posted count to Discord Bots!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // BotListSpace

            Request botlistspace = new Request.Builder()
                    .url(BotListSpace)
                    .post(body)
                    .addHeader("Authorization", config.getBotListSpaceToken())
                    .build();

            try {
                new OkHttpClient().newCall(botlistspace).execute().close();
                System.out.println("Successfully posted count to BotList.Space!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Discord Bots 2

            Request discordbots2 = new Request.Builder()
                    .url(DiscordBots2)
                    .post(body)
                    .addHeader("Authorization", config.getDiscordBots2Token())
                    .build();

            try {
                new OkHttpClient().newCall(discordbots2).execute().close();
                System.out.println("Successfully posted count to discord.bots.gg!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // CloudList

            Request cloudlist = new Request.Builder()
                    .url(CloudList)
                    .post(body)
                    .addHeader("Authorization", config.getCloudListToken())
                    .build();

            try {
                new OkHttpClient().newCall(cloudlist).execute().close();
                System.out.println("Successfully posted count to the CloudList!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
