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
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        update(event);
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {
        update(event);
    }

    public void update(GenericMessageReactionEvent event) {
        if (event.getReaction().getReactionEmote().getName().equals("⭐")) {
            if (!rethink.hasStarboardMessage(event.getMessageId())) {
                if (rethink.hasStarboardChannel(event.getGuild().getId())) {

                    event.getTextChannel().retrieveMessageById(event.getMessageId()).queue(
                            msg -> {
                                Integer stars = 0;
                                for (MessageReaction reaction : msg.getReactions()) {
                                    if (reaction.getReactionEmote().getName().equals("⭐")) {
                                        stars = reaction.getCount();
                                    }
                                }

                                if (Integer.parseInt(rethink.getNeededstars(event.getGuild().getId())) <= stars) {
                                    event.getGuild().getTextChannelById(rethink.getStarboardChannel(event.getGuild().getId()))
                                            .sendMessage(new MessageBuilder()
                                                    .setContent("⭐ 1" + " " + event.getTextChannel().getAsMention())
                                                    .setEmbed(
                                                            new EmbedBuilder()
                                                                    .setAuthor(msg.getAuthor().getAsTag())
                                                                    .setDescription(msg.getContentRaw())
                                                                    .setTimestamp(msg.getTimeCreated()).build()).build()).queue(
                                            starboardmsg -> {
                                                rethink.insertStarboardMessage(msg.getId(), event.getGuild().getId(), starboardmsg.getId());
                                            }
                                    );
                                }
                            }
                    );

                }
            } else {

                event.getTextChannel().retrieveMessageById(event.getMessageId()).queue(
                        msg -> {
                            Integer stars = 0;
                            for (MessageReaction reaction : msg.getReactions()) {
                                if (reaction.getReactionEmote().getName().equals("⭐")) {
                                    stars = reaction.getCount();
                                }
                            }

                            Integer finalStars = stars;
                            event.getGuild().getTextChannelById(rethink.getStarboardChannel(event.getGuild().getId()))
                                    .retrieveMessageById(rethink.getStarboardMessage(event.getMessageId())).queue(
                                    msg2 -> {

                                        if (Integer.parseInt(rethink.getNeededstars(event.getGuild().getId())) <= finalStars) {
                                            msg2.editMessage(new MessageBuilder()
                                                    .setContent("⭐ " + finalStars + " " + event.getTextChannel().getAsMention())
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
