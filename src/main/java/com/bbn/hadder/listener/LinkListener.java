package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.LinkUtils;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class LinkListener extends ListenerAdapter {

    Rethink rethink;

    public LinkListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(
                msg -> {
                    if (event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                        if (!event.getMember().getUser().equals(event.getJDA().getSelfUser())) {
                            if (msg.getAuthor().equals(event.getJDA().getSelfUser())) {
                                if (msg.getEmbeds().size() == 1) {
                                    if (msg.getEmbeds().get(0).getFooter() == null) {
                                        if (msg.getEmbeds().get(0).getTitle().endsWith(") wants to link guilds!")) {
                                            String requestguild = msg.getEmbeds().get(0).getTitle().replaceAll("\\) wants to link guilds!", "");
                                            String requestguildid = null;
                                            for (int i = requestguild.length() - 1; i >= 0; i--) {
                                                if (String.valueOf(requestguild.charAt(i)).equals("(")) {
                                                    requestguildid = requestguild.substring(i + 1);
                                                    break;
                                                }
                                            }
                                            if (requestguildid != null) {
                                                if (event.getReactionEmote().getName().equals("✅")) {
                                                    rethink.addLinkedGuild(event.getGuild().getId(), requestguildid);
                                                    rethink.addLinkedGuild(requestguildid, event.getGuild().getId());

                                                    msg.delete().queue();

                                                    MessageEmbed msgembed = new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO,
                                                            new EmbedBuilder().setTitle("Guilds linked!").setDescription("The Guild allowed the link. Have fun!")).build();
                                                    event.getChannel().sendMessage(msgembed).queue();
                                                    event.getJDA().getTextChannelById(rethink.getLinkChannel(requestguildid)).sendMessage(msgembed).queue();
                                                } else if (event.getReactionEmote().getName().equals("❌")) {
                                                    msg.delete().queue();

                                                    MessageEmbed msgembed = new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO,
                                                            new EmbedBuilder().setTitle("Guild linking denied.").setDescription("The Guild denied the link. :(")).build();
                                                    event.getChannel().sendMessage(msgembed).queue();
                                                    event.getJDA().getTextChannelById(rethink.getLinkChannel(requestguildid)).sendMessage(msgembed).queue();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.isWebhookMessage()) {
            if (event.getChannel().getId().equals(rethink.getLinkChannel(event.getGuild().getId()))) {
                new LinkUtils().sendAll(rethink.getLinks(event.getGuild().getId()), event.getJDA(), event.getMessage(), event.getAuthor(), new ArrayList<>() {
                }, rethink, false);
            }
        }
    }
}
