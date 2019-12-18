package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
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
                    event.getChannel().retrieveMessageById(event.getMessageId()).queue(msg -> {
                        event.getGuild().getTextChannelById(rethink.getStarboardChannel(event.getGuild().getId()))
                                .sendMessage(new MessageBuilder()
                                        .setContent("⭐1" + " " + event.getTextChannel().getAsMention())
                                        .setEmbed(
                                                new EmbedBuilder()
                                                        .setAuthor(event.getUser().getAsTag())
                                                        .setDescription(msg.getContentRaw())
                                                        .setTimestamp(msg.getTimeCreated()).build()).build()).queue(
                                starboardmsg -> {
                                    rethink.insertStarboardMessage(msg.getId(), event.getGuild().getId(), starboardmsg.getId());
                                }
                        );
                    });
                }
            } else {
                event.getGuild().getTextChannelById(rethink.getStarboardChannel(event.getGuild().getId()))
                        .retrieveMessageById(rethink.getStarboardMessage(event.getMessageId())).queue(
                        msg -> {
                            msg.editMessage(new MessageBuilder()
                                    .setContent("⭐" + event.getReaction().getCount() + " " + event.getTextChannel().getAsMention())
                                    .setEmbed(
                                            new EmbedBuilder()
                                                    .setAuthor(event.getUser().getAsTag())
                                                    .setDescription(msg.getContentRaw())
                                                    .setTimestamp(msg.getTimeCreated()).build()).build()).queue();
                        }
                );
            }
        }
    }
}
