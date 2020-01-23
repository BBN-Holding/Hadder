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
import com.bbn.hadder.RethinkServer;
import com.bbn.hadder.RethinkUser;
import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.core.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

public class CommandListener extends ListenerAdapter {

    private Rethink rethink;
    private CommandHandler handler;
    private AudioManager audioManager;

    public CommandListener(Rethink rethink, CommandHandler handler, AudioManager audioManager) {
        this.rethink = rethink;
        this.handler = handler;
        this.audioManager = audioManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().isBot()) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
                    RethinkUser rethinkUser = new RethinkUser(rethink.getObjectByID("user", event.getAuthor().getId()), rethink);
                    RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", event.getGuild().getId()), rethink);
                    rethinkUser.push();
                    rethinkServer.push();
                    String[] prefixes = {
                            rethinkUser.getPrefix(), rethinkServer.getPrefix(),
                            event.getGuild().getSelfMember().getAsMention() + " ", event.getGuild().getSelfMember().getAsMention(),
                            event.getGuild().getSelfMember().getAsMention().replace("@", "@!") + " ",
                            event.getGuild().getSelfMember().getAsMention().replace("@", "@!")
                    };
                    for (String prefix : prefixes) {
                        if (event.getMessage().getContentRaw().startsWith(prefix)) {
                            handler.handle(event, rethink, prefix, audioManager, rethinkUser, rethinkServer);
                            return;
                        }
                    }
                } else {
                    event.getAuthor().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                            .setTitle("No permission")
                            .setDescription("I need the `MESSAGE EMBED LINKS` permission in order to work!")
                            .setColor(Color.RED)
                            .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                            .setTimestamp(Instant.now())
                            .build()).queue();
                }
            } else {
                event.getAuthor().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                        .setTitle("No permission")
                        .setDescription("I need the `MESSAGE WRITE` permission in order to work!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now())
                        .build()).queue();
            }
        }
    }
}
