/*
 * Copyright 2019-2021 GregTCLTK and Schlauer-Hax
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

package one.bbn.hadder.listener;

import one.bbn.hadder.audio.AudioManager;
import one.bbn.hadder.core.CommandHandler;
import one.bbn.hadder.db.Mongo;
import one.bbn.hadder.db.MongoServer;
import one.bbn.hadder.db.MongoUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

public class CommandListener extends ListenerAdapter {

    private final Mongo mongo;
    private final CommandHandler handler;
    private final AudioManager audioManager;

    public CommandListener(Mongo mongo, CommandHandler handler, AudioManager audioManager) {
        this.mongo = mongo;
        this.handler = handler;
        this.audioManager = audioManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromType(ChannelType.TEXT) && !e.getAuthor().isBot()) {
            if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
                    MongoUser mongoUser = new MongoUser(mongo.getObjectByID("user", e.getAuthor().getId()), mongo);
                    MongoServer mongoServer = new MongoServer(mongo.getObjectByID("server", e.getGuild().getId()), mongo);
                    mongoUser.push();
                    mongoServer.push();
                    String[] prefixes = {
                            mongoUser.getPrefix(), mongoServer.getPrefix(),
                            e.getGuild().getSelfMember().getAsMention() + " ", e.getGuild().getSelfMember().getAsMention(),
                            e.getGuild().getSelfMember().getAsMention().replace("@", "@!") + " ",
                            e.getGuild().getSelfMember().getAsMention().replace("@", "@!")
                    };
                    for (String prefix : prefixes) {
                        if (e.getMessage().getContentRaw().startsWith(prefix)) {
                            handler.handle(e, mongo, prefix, audioManager, mongoUser, mongoServer);
                            return;
                        }
                    }
                } else {
                    try {
                        e.getAuthor().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                                .setTitle("No permission")
                                .setDescription("I need the `MESSAGE EMBED LINKS` permission in order to work!")
                                .setColor(Color.RED)
                                .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                                .setTimestamp(Instant.now())
                                .build()).queue();
                    } catch (ErrorResponseException ex) {
                        e.getTextChannel().sendMessage("I need the `MESSAGE EMBED LINKS` permission in order to work!").queue();
                    }
                }
            } else {
                try {
                    e.getAuthor().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                            .setTitle("No permission")
                            .setDescription("I need the `MESSAGE WRITE` permission in order to work!")
                            .setColor(Color.RED)
                            .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                            .setTimestamp(Instant.now())
                            .build()).queue();
                } catch (ErrorResponseException ignore) {}

            }
        }
    }
}
