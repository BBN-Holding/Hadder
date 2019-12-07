package com.bbn.hadder.commands.nsfw;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class BDSMCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getTextChannel().isNSFW()) {

            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("https://api.nekos.dev/api/v3/images/nsfw/img/bdsm_lewd").build();

            try {

                Response response = caller.newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                JSONObject data = json.getJSONObject("data");
                JSONObject response1 = data.getJSONObject("response");
                String url = response1.toString().replace("{\"url\":\"", "");

                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                        .setAuthor("Image not showing? Click here", url.replace("\"}", ""))
                        .setImage(url.replace("\"}", ""))
                        .setFooter("BDSM")
                        .build()).queue();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING).setTitle("No NSFW").setDescription("You can only execute this command in NSFW channels!").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"bdsm"};
    }

    @Override
    public String description() {
        return "Shows a random bdsm picture.";
    }

    @Override
    public String usage() {
        return "";
    }
}