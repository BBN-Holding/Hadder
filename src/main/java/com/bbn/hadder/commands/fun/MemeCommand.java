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
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class MemeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://some-random-api.ml/meme").build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            String url = json.get("image").toString();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder())
                    .setTitle("Your random meme")
                    .setImage(url)
                    .build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR, new EmbedBuilder())
                    .setTitle("Error!")
                    .setDescription("The request to the meme API could not be processed. Please try it again later.")
                    .setColor(Color.RED)
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"meme"};
    }

    @Override
    public String description() {
        return "Sends you a random meme.";
    }

    @Override
    public String usage() {
        return "";
    }
}
