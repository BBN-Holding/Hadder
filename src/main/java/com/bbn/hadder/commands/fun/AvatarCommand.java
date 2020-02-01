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

package com.bbn.hadder.commands.fun;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class AvatarCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 0) {
            User u  = e.getAuthor();
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.fun.avatar.success.title",
                            u.getAsTag(),
                            "",
                            "")
                    .setImage(u.getAvatarUrl())
                    .setFooter(u.getAsTag())
                    .build()).queue();
        } else if (e.getMessage().getMentionedUsers().size() == 1) {
            User u = e.getMessage().getMentionedUsers().get(0);
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.fun.avatar.success.title",
                            u.getAsTag(),
                            "",
                            "")
                    .setImage(u.getAvatarUrl())
                    .setFooter(u.getAsTag())
                    .build()).queue();
        } else if (args[0].length() == 18) {
            try {
                User u = e.getJDA().getUserById(args[0]);
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.fun.avatar.success.title",
                                u.getAsTag(),
                                "",
                                "")
                                .setImage(u.getAvatarUrl())
                                .setFooter(u.getAsTag())
                                .build()).queue();
            } catch (NullPointerException ignore) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://canary.discordapp.com/api/v6/users/" + args[0]).addHeader("Authorization", "Bot " + e.getConfig().getBotToken()).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONObject json = new JSONObject(response.body().string());
                    String url = "https://cdn.discordapp.com/avatars/" + args[0] + "/" + json.getString("avatar") + ".png";
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.INFO,
                                            "commands.fun.avatar.success.title",
                                            json.getString("username") + "#" + json.getString("discriminator"),
                                            "",
                                            "")
                                            .setImage(url)
                                            .setFooter(json.getString("username") + "#" + json.getString("discriminator"))
                                            .build()).queue();
                } catch (JSONException ex) {
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                            "commands.fun.avatar.error.title",
                            "commands.fun.avatar.error.description"
                    ).build()).queue();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"avatar"};
    }

    @Override
    public String description() {
        return "commands.fun.avatar.help.description";
    }

    @Override
    public String usage() {
        return "[user]";
    }

    @Override
    public String example() {
        return "@Skidder";
    }
}
