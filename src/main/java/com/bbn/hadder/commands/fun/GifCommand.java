package com.bbn.hadder.commands.fun;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class GifCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            StringBuilder query = new StringBuilder();
            for (String arg : args) {
                query.append(arg.toLowerCase()).append("+");
                query = new StringBuilder(query.substring(0, query.length() - 1));
            }

            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=" + event.getConfig().getGiphyToken()).build();
            try {
                Random rand = new Random();
                Response response = caller.newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                JSONArray array = json.getJSONArray("data");
                int gifIndex = rand.nextInt(array.length());
                String url = array.getJSONObject(gifIndex).get("url").toString();
                event.getTextChannel().sendMessage(url).queue();
            } catch (Exception e) {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR, builder).setTitle("Error").setDescription("Please try again with another term.").build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You have to write at least one search term!").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"gif"};
    }

    @Override
    public String description() {
        return "Look for a GIF on Giphy";
    }

    @Override
    public String usage() {
        return "<SearchTerm>";
    }
}
