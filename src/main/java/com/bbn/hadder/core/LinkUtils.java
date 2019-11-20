package com.bbn.hadder.core;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LinkUtils {

    public void sendAll(JSONArray jsonArray, JDA jda, Message message, User user, List<String> reactions) {
        for (int i = 0; jsonArray.length() > i; i++) {
            this.send(message, jda.getTextChannelById(jsonArray.getString(i)), user, reactions);
        }
    }

    private void send(Message message, TextChannel channel, User user, List<String> reactions) {

        channel.retrieveWebhooks().queue(
                webhooks -> {
                    Webhook webhook=null;
                    for (Webhook webhooktemp : webhooks) {
                        if (webhooktemp.getOwner().equals(channel.getJDA().getSelfUser())) {
                            webhook=webhooktemp;
                        }
                    }

                    if (webhook==null) {
                        channel.createWebhook("Hadder GuildLink").queue(
                                webhook1 -> sendMessage(webhook1, channel, user, message, reactions)
                        );
                    } else sendMessage(webhook, channel, user, message, reactions);
                }
        );


    }

    private void sendMessage(Webhook webhook, TextChannel channel, User user, Message message, List<String> reactions) {
        WebhookClientBuilder builder = new WebhookClientBuilder(webhook.getId());
        WebhookClient client = builder.build();
        WebhookMessageBuilder mb = new WebhookMessageBuilder();
        mb.setUsername(user.getName())
                .setAvatarUrl((user.getAvatarUrl()!=null) ? user.getAvatarUrl() : user.getDefaultAvatarUrl())
                .setContent(message.getContentRaw());
        for (Message.Attachment attachment:message.getAttachments()) {
            try {
                mb.addFile(attachment.getFileName(), attachment.retrieveInputStream().get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        for (MessageEmbed embed : message.getEmbeds()) {
            List<WebhookEmbed.EmbedField> fields = new ArrayList<>();
            for (MessageEmbed.Field field : embed.getFields()) {
                fields.add(new WebhookEmbed.EmbedField(field.isInline(), field.getName(), field.getValue()));
            }
            mb.addEmbeds(new WebhookEmbed(
                    embed.getTimestamp(), embed.getColorRaw(), embed.getDescription(),
                    embed.getThumbnail().getUrl(), embed.getImage().getUrl(), new WebhookEmbed.EmbedFooter(embed.getFooter().getText(), embed.getFooter().getIconUrl()),
                    new WebhookEmbed.EmbedTitle(embed.getTitle(), embed.getUrl()),
                    new WebhookEmbed.EmbedAuthor(embed.getAuthor().getName(), embed.getAuthor().getIconUrl(), embed.getAuthor().getUrl()), fields));
        }
        try {
            long msgid = client.send(mb.build()).get().getId();
            channel.retrieveMessageById(msgid).queue(
                    msg -> {
                        for (String reaction: reactions) {
                            msg.addReaction(reaction).queue();
                        }
                    }
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
