package com.bbn.hadder.commands.misc;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GitHubCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (args[0].equals("link")) {
                event.getTextChannel().sendMessage(
                        event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.misc.github.link.title",
                                "")
                                .setDescription("https://github.com/login/oauth/authorize?client_id=25321f690bb1b6952942")
                                .build()
                ).queue();
            } else {
                Request request = new Request.Builder().url("https://api.github.com/users/" + args[0]).build();
                try {

                    Response response = new OkHttpClient().newCall(request).execute();
                    JSONObject json = new JSONObject(response.body().string());

                    String nickname = json.getString("name");
                    String bio = "None";
                    String location = "None";
                    String website = "None";
                    try {
                        bio = json.getString("bio");
                    } catch (JSONException ignored) {
                    }
                    try {
                        location = json.getString("location");
                    } catch (JSONException ignored) {
                    }

                    if (!json.getString("blog").equals("")) website = json.getString("blog");

                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                            .setAuthor(event.getMessageEditor().getTerm("commands.misc.github.success.title", nickname + " (" + args[0] + ")", ""), "https://github.com/" + args[0] + "", "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                            .setThumbnail(json.getString("avatar_url"))
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.bio"), bio, false)
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.location"), location, true)
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.website"), website, true)
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.repositories"), String.valueOf(json.getInt("public_repos")), true)
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.gists"), String.valueOf(json.getInt("public_gists")), true)
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.followers"), String.valueOf(json.getInt("followers")), true)
                            .addField(event.getMessageEditor().getTerm("commands.misc.github.success.following"), String.valueOf(json.getInt("following")), true)
                            .build()).queue();

                } catch (IOException | NullPointerException e) {
                    event.getTextChannel().sendMessage(
                            event.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.ERROR,
                                    "commands.misc.github.api.error.title",
                                    "commands.misc.github.api.error.description")
                                    .build()
                    ).queue();
                } catch (JSONException e) {
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.ERROR,
                            "commands.misc.github.user.error.title",
                            "commands.misc.github.user.error.description").build()).queue();
                }
            }
        } else {
            event.getTextChannel().sendMessage(
                    event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.misc.github.connect.title",
                            "",
                            "commands.misc.github.connect.description",
                            "(https://github.com/login/oauth/authorize?client_id=25321f690bb1b6952942)")
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"github"};
    }

    @Override
    public String description() {
        return "commands.misc.github.help.description";
    }

    @Override
    public String usage() {
        return "[GH name]";
    }

    @Override
    public String example() {
        return "GregTCLTK";
    }
}
