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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class StarboardListener extends ListenerAdapter {

    private Rethink rethink;

    public StarboardListener(Rethink rethink) {
        this.rethink = rethink;
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
            RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", e.getGuild().getId()), rethink);
            if (!rethink.hasStarboardMessage(e.getMessageId())) {
                if (rethink.hasStarboardChannel(e.getGuild().getId())) {
                    e.getTextChannel().retrieveMessageById(e.getMessageId()).queue(
                            msg -> {
                                int stars = 0;
                                for (MessageReaction reaction : msg.getReactions()) {
                                    if (reaction.getReactionEmote().getName().equals("⭐")) {
                                        stars = reaction.getCount();
                                    }
                                }

                                if (Integer.parseInt(rethinkServer.getNeededstars()) <= stars) {
                                    e.getGuild().getTextChannelById(rethinkServer.getStarboard())
                                            .sendMessage(new MessageBuilder()
                                                    .setContent("⭐ 1" + " " + e.getTextChannel().getAsMention())
                                                    .setEmbed(
                                                            new EmbedBuilder()
                                                                    .setAuthor(msg.getAuthor().getAsTag())
                                                                    .setDescription(msg.getContentRaw())
                                                                    .setTimestamp(msg.getTimeCreated()).build()).build()).queue(
                                            starboardmsg -> {
                                                rethink.insertStarboardMessage(msg.getId(), e.getGuild().getId(), starboardmsg.getId());
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
                            e.getGuild().getTextChannelById(rethinkServer.getStarboard())
                                    .retrieveMessageById(rethink.getStarboardMessage(e.getMessageId())).queue(
                                    msg2 -> {

                                        if (Integer.parseInt(rethinkServer.getNeededstars()) <= finalStars) {
                                            msg2.editMessage(new MessageBuilder()
                                                    .setContent("⭐ " + finalStars + " " + e.getTextChannel().getAsMention())
                                                    .setEmbed(
                                                            new EmbedBuilder()
                                                                    .setAuthor(msg.getAuthor().getAsTag())
                                                                    .setDescription(msg.getContentRaw())
                                                                    .setTimestamp(msg.getTimeCreated()).build()).build()).queue();
                                        } else {
                                            msg2.delete().queue();
                                            rethink.removeStarboardMessage(msg.getId());
                                        }
                                    }
                            );
                        }
                );
            }
        }
    }
}
