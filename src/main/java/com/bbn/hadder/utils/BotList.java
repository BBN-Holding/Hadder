package com.bbn.hadder.utils;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Hadder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotList {

    private static String MythicalBotList = "https://mythicalbots.xyz/api";

    private static JSONObject json = new JSONObject();

    public static void post() {
        json.put("server_count", Hadder.shardManager.getGuilds().size());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

        File configfile = new File("./config.json");

        JSONObject config = null;
        try {
            config = new JSONObject(new String(Files.readAllBytes(Paths.get(configfile.toURI()))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mythical Bot List
        Request mythicalbotlist = new Request.Builder()
                .url(MythicalBotList)
                .post(body)
                .addHeader("Authorization", config.getString("MythicalBotList"))
                .build();

        try {
            new OkHttpClient().newCall(mythicalbotlist).execute().close();
            System.out.println("Successfully posted count for the Mythical Bot List!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
