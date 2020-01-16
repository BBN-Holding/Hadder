package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Skidder / GregTCLTK
 */

public class WakaTimeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            Request request = new Request.Builder().url("https://wakatime.com/api/v1/users/" + args[0]).addHeader("Authorization", "Basic " + event.getConfig().getWakaTimeToken()).build();

            try {
                Response response = new OkHttpClient().newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "",
                        "").build()).queue();
            } catch (JSONException e) {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.ERROR,
                        "commands.misc.wakatime.user.error.title",
                        "commands.misc.wakatime.user.error.description").build()).queue();
            } catch (IOException | NullPointerException e) {
                event.getTextChannel().sendMessage(
                        event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "commands.misc.wakatime.api.error.title",
                                "commands.misc.wakatime.api.error.description")
                                .build()
                ).queue();
            }
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"wakatime", "wk"};
    }

    @Override
    public String description() {
        return "commands.misc.wakatime.help.description";
    }

    @Override
    public String usage() {
        return "[WakaTime-User]";
    }

    @Override
    public String example() {
        return "@Skidder";
    }
}
