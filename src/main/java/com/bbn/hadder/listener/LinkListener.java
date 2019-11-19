package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.LinkUtils;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;

import javax.annotation.Nonnull;

public class LinkListener extends ListenerAdapter {

    Rethink rethink;

    public LinkListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if (event.isFromGuild()) {
            event.getTextChannel().retrieveMessageById(event.getMessageId()).queue(
                    msg -> {
                        if (msg.getAuthor().equals(event.getJDA().getSelfUser())) {
                            if (msg.getEmbeds().size() == 1) {
                                String footer = msg.getEmbeds().get(0).getFooter().getText();
                                if (footer!=null) {
                                    if (footer.startsWith("Request by: ")&&footer.contains(" To:")) {
                                        String[] ids = footer.replace("Request by: ", "").replace("To:", "").split(" ");
                                        JSONArray jsonArray = rethink.getLinks(ids[0]);
                                        for (int i = 0; jsonArray.length()>i; i++) {
                                            rethink.addLinkedGuild(jsonArray.getString(i), ids[1]);
                                            rethink.addLinkedGuild(ids[1], jsonArray.getString(i));
                                        }

                                        rethink.addLinkedGuild(ids[0], ids[1]);

                                        new LinkUtils().sendAll(rethink.getLinks(event.getGuild().getId()), event.getJDA(),
                                                new MessageBuilder().setEmbed(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO,
                                                        new EmbedBuilder().setTitle(event.getGuild().getName()+" just joined the link!").setDescription("Say Hello!")).build()).build(), event.getJDA().getSelfUser());
                                    }
                                }
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        // TODO
    }
}
