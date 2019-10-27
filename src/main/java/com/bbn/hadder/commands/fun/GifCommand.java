package com.bbn.hadder.commands.fun;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class GifCommand implements Command {
    @Override
    public void executed(String[] args, MessageReceivedEvent event) {
        String url;
        JSONArray array;
        String query = "";
        for(String arg : args) {
            query += arg.toLowerCase() + "+";
            query = query.substring(0, query.length()-1);
        }

        File configfile = new File("./config.json");

        JSONObject config = null;
        try {
            config = new JSONObject(new String(Files.readAllBytes(Paths.get(configfile.toURI()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=" + config.getString("Giphy")).build();
        try {
            Random rand = new Random();
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            array = json.getJSONArray("data");
            int gifIndex = rand.nextInt(array.length());
            url = (String) array.getJSONObject(gifIndex).get("url");
            event.getTextChannel().sendMessage(url).queue();
        } catch (Exception e) {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.ERROR, builder).setTitle("Error").setDescription("Please try again with another term.").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"gif"};
    }
}
