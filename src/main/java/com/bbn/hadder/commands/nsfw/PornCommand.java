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

public class PornCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getTextChannel().isNSFW()) {

            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("https://api.nekos.dev/api/v3/images/nsfw/gif/classic/").build();

            try {

                Response response = caller.newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                JSONObject data = json.getJSONObject("data");
                JSONObject response1 = data.getJSONObject("response");
                String url = response1.toString().replace("{\"url\":\"", "");

                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                        .setAuthor(event.getMessageEditor().getTerm("commands.nsfw.gif.error.title"), url.replace("\"}", ""))
                        .setImage(url.replace("\"}", ""))
                        .setFooter("Porn")
                        .build()).queue();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_NSFW).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"porn"};
    }

    @Override
    public String description() {
        return "commands.nsfw.porn.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
