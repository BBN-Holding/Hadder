/*
 * Copyright 2019-2020 GregTCLTK and Schlauer-Hax
 *
 * Licensed under the GNU Affero General Public License, Version 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.gnu.org/licenses/agpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class GitHubCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            if (args[0].equals("link")) {
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
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
                    } catch (JSONException ignored) {}
                    try {
                        location = json.getString("location");
                    } catch (JSONException ignored) {}

                    if (!json.getString("blog").equals("")) website = json.getString("blog");

                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                            .setAuthor(e.getMessageEditor().getTerm("commands.misc.github.success.title", nickname + " (" + args[0] + ")", ""), "https://github.com/" + args[0] + "", "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                            .setThumbnail(json.getString("avatar_url"))
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.bio"), bio, false)
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.location"), location, true)
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.website"), website, true)
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.repositories"), String.valueOf(json.getInt("public_repos")), true)
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.gists"), String.valueOf(json.getInt("public_gists")), true)
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.followers"), String.valueOf(json.getInt("followers")), true)
                            .addField(e.getMessageEditor().getTerm("commands.misc.github.success.following"), String.valueOf(json.getInt("following")), true)
                            .build()).queue();

                } catch (IOException | NullPointerException ex) {
                    e.getTextChannel().sendMessage(
                            e.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.ERROR,
                                    "commands.misc.github.api.error.title",
                                    "commands.misc.github.api.error.description")
                                    .build()
                    ).queue();
                } catch (JSONException ex) {
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.ERROR,
                            "commands.misc.github.user.error.title",
                            "commands.misc.github.user.error.description").build()).queue();
                }
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
            /*
            TODO: THIS
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.misc.github.connect.title",
                            "",
                            "commands.misc.github.connect.description",
                            "(https://github.com/login/oauth/authorize?client_id=25321f690bb1b6952942)")
                    .build()).queue(); */
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
        return "[user]";
    }

    @Override
    public String example() {
        return "GregTCLTK";
    }
}
