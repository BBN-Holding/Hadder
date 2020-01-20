package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
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
            if (!rethink.hasStarboardMessage(e.getMessageId())) {
                if (rethink.hasStarboardChannel(e.getGuild().getId())) {

                    e.getTextChannel().retrieveMessageById(e.getMessageId()).queue(
                            msg -> {
                                Integer stars = 0;
                                for (MessageReaction reaction : msg.getReactions()) {
                                    if (reaction.getReactionEmote().getName().equals("⭐")) {
                                        stars = reaction.getCount();
                                    }
                                }

                                if (Integer.parseInt(rethink.getNeededStars(e.getGuild().getId())) <= stars) {
                                    e.getGuild().getTextChannelById(rethink.getStarboardChannel(e.getGuild().getId()))
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
                            Integer stars = 0;
                            for (MessageReaction reaction : msg.getReactions()) {
                                if (reaction.getReactionEmote().getName().equals("⭐")) {
                                    stars = reaction.getCount();
                                }
                            }

                            Integer finalStars = stars;
                            e.getGuild().getTextChannelById(rethink.getStarboardChannel(e.getGuild().getId()))
                                    .retrieveMessageById(rethink.getStarboardMessage(e.getMessageId())).queue(
                                    msg2 -> {

                                        if (Integer.parseInt(rethink.getNeededStars(e.getGuild().getId())) <= finalStars) {
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
