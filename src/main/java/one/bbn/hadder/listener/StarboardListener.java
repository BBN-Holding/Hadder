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

import one.bbn.hadder.db.Mongo;
import one.bbn.hadder.db.MongoServer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class StarboardListener extends ListenerAdapter {

    private final Mongo mongo;

    public StarboardListener(Mongo mongo) {
        this.mongo = mongo;
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent e) {
        update(e);
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent e) {
        update(e);
    }

    public void update(GenericMessageReactionEvent e) {
        if (e.getReaction().getReactionEmote().getName().equals("⭐")) {
            MongoServer mongoServer = new MongoServer(mongo.getObjectByID("server", e.getGuild().getId()), mongo);
            if (!mongo.hasStarboardMessage(e.getMessageId())) {
                if (mongoServer.hasStarboardChannel()) {
                    e.getTextChannel().retrieveMessageById(e.getMessageId()).queue(
                            msg -> {
                                int stars = 0;
                                for (MessageReaction reaction : msg.getReactions()) {
                                    if (reaction.getReactionEmote().getName().equals("⭐")) {
                                        stars = reaction.getCount();
                                    }
                                }

                                if (Integer.parseInt(mongoServer.getNeededStars()) <= stars) {
                                    e.getGuild().getTextChannelById(mongoServer.getStarboard())
                                            .sendMessage(new MessageBuilder()
                                                    .setContent("⭐ 1" + " " + e.getTextChannel().getAsMention())
                                                    .setEmbed(
                                                            new EmbedBuilder()
                                                                    .setAuthor(msg.getAuthor().getAsTag())
                                                                    .setDescription(msg.getContentRaw())
                                                                    .setTimestamp(msg.getTimeCreated()).build()).build()).queue(
                                            starboardmsg -> {
                                                mongo.insertStarboardMessage(msg.getId(), e.getGuild().getId(), starboardmsg.getId());
                                            }
                                    );
                                }
                            }
                    );

                }
            } else {

                e.getTextChannel().retrieveMessageById(e.getMessageId()).queue(
                        msg -> {
                            int stars = 0;
                            for (MessageReaction reaction : msg.getReactions()) {
                                if (reaction.getReactionEmote().getName().equals("⭐")) {
                                    stars = reaction.getCount();
                                }
                            }

                            int finalStars = stars;
                            e.getGuild().getTextChannelById(mongoServer.getStarboard())
                                    .retrieveMessageById(mongo.getStarboardMessage(e.getMessageId())).queue(
                                    msg2 -> {

                                        if (Integer.parseInt(mongoServer.getNeededStars()) <= finalStars) {
                                            msg2.editMessage(new MessageBuilder()
                                                    .setContent("⭐ " + finalStars + " " + e.getTextChannel().getAsMention())
                                                    .setEmbed(
                                                            new EmbedBuilder()
                                                                    .setAuthor(msg.getAuthor().getAsTag())
                                                                    .setDescription(msg.getContentRaw())
                                                                    .setTimestamp(msg.getTimeCreated()).build()).build()).queue();
                                        } else {
                                            msg2.delete().queue();
                                            mongo.removeStarboardMessage(msg.getId());
                                        }
                                    }
                            );
                        }
                );
            }
        }
    }
}
