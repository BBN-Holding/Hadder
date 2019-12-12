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

public class BlowjobCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getTextChannel().isNSFW()) {

            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("https://nekos.life/api/v2/img/blowjob").build();

            try {

                Response response = caller.newCall(request).execute();
                String url = response.body().string().replace("{\"url\":\"", "");

                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                        .setAuthor(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.nsfw.img.error.title"), url.replace("\"}", ""))
                        .setImage(url.replace("\"}", ""))
                        .setFooter("Blowjob")
                        .build()).queue();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_NSFW).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"blowjob"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.nsfw.blowjob.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
