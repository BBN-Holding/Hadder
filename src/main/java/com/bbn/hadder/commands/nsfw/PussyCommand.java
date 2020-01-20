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

import java.io.IOException;

public class PussyCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getTextChannel().isNSFW()) {

            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("https://nekos.life/api/v2/img/pussy").build();

            try {

                Response response = caller.newCall(request).execute();
                String url = response.body().string().replace("{\"url\":\"", "");

                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                        .setAuthor(e.getMessageEditor().getTerm("commands.nsfw.gif.error.title"), url.replace("\"}", ""))
                        .setImage(url.replace("\"}", ""))
                        .setFooter("Pussy")
                        .build()).queue();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_NSFW).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"pussy"};
    }

    @Override
    public String description() {
        return "commands.nsfw.pussy.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
