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

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Perms(Perm.MANAGE_WEBHOOKS)
public class ClydeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            if (e.getGuild().getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
                TextChannel channel = e.getMessage().getTextChannel();
                String content = e.getMessage().getContentRaw().replace(e.getRethinkServer().getPrefix(), "").replace(e.getRethinkUser().getPrefix(), "").replace("clyde", "");

                Webhook webhook = channel.createWebhook(e.getConfig().getClydeName()).complete();
                try {
                    InputStream s = new URL("https://discordapp.com/assets/f78426a064bc9dd24847519259bc42af.png").openStream();
                    webhook.getManager().setAvatar(Icon.from(s)).queue();

                    WebhookClientBuilder builder = new WebhookClientBuilder(webhook.getUrl());

                    WebhookClient client = builder.build();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    client.send(content);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                webhook.delete().queue();
                e.getMessage().delete().queue();
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);

    }

    @Override
    public String[] labels() {
        return new String[]{"clyde"};
    }

    @Override
    public String description() {
        return "commands.fun.clyde.help.description";
    }

    @Override
    public String usage() {
        return "[content]";
    }

    @Override
    public String example() {
        return "Hey I am Clyde";
    }
}
