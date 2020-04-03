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

public class CodeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://canary.discordapp.com/api/v6/invite/" + args[0] + "?with_counts=true").addHeader("Authorization", "Bot " + e.getConfig().getBotToken()).build();

            try {
                Response response = client.newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.misc.code.success.title",
                        "commands.misc.code.success.description")
                        .addField("Code", "[" + args[0] + "](https://discord.gg/" + args[0] + ")", true)
                        .addField("Guild Name", json.getJSONObject("guild").getString("name"), true)
                        .addBlankField(true)
                        .addField("Verification Level", String.valueOf(json.getJSONObject("guild").getInt("verification_level")), true)
                        .addField("Guild ID", json.getJSONObject("guild").getString("id"), true)
                        .addBlankField(true)
                        .addField("Members", String.valueOf(json.get("approximate_member_count")), true)
                        .addBlankField(true)
                        .addBlankField(true)
                        .setThumbnail("https://cdn.discordapp.com/icons/" + json.getJSONObject("guild").getString("id") + "/" + json.getJSONObject("guild").getString("icon") + ".png")
                        .build()).queue();
            } catch (JSONException ex) {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.misc.code.error.title",
                        "commands.misc.code.error.description").build()).queue();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"code", "invite-code", "ic"};
    }

    @Override
    public String description() {
        return "commands.misc.code.help.description";
    }

    @Override
    public String usage() {
        return "[Invite-code]";
    }

    @Override
    public String example() {
        return "58My2dM";
    }
}
