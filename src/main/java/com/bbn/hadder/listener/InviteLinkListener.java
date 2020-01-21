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

package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.annotation.Nonnull;

public class InviteLinkListener extends ListenerAdapter {

    private Rethink rethink;

    public InviteLinkListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        if (e.isFromType(ChannelType.TEXT)) {
            if (e.getMessage().getContentRaw().contains("discord.gg/") && (!e.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(e.getGuild().getId()))) {
                checkInvite(e.getMessage(), "discord.gg/");
            } else if (e.getMessage().getContentRaw().contains("discordapp.com/invite") && (!e.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(e.getGuild().getId()))) {
                checkInvite(e.getMessage(), "discordapp.com/invite/");
            }
        }
    }

    public void checkInvite(Message message, String regex) {
        String split = message.getContentRaw().split(regex, 10)[1];
        String invite = split.split(" ")[0];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://canary.discordapp.com/api/v6/invites/" + invite).build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            if (!json.toString().contains("\"message\":")) {
                message.delete().reason("Invite Link detected").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent e) {
        if (e.isFromType(ChannelType.TEXT)) {
            if (e.getMessage().getContentRaw().contains("discord.gg/") && !e.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(e.getGuild().getId())) {
                checkInvite(e.getMessage(), "discord.gg/");
            } else if (e.getMessage().getContentRaw().contains("discordapp.com/invite") && !e.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(e.getGuild().getId())) {
                checkInvite(e.getMessage(), "discordapp.com/invite/");
            }
        }
    }
}
