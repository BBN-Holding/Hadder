package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class PrivateMessageListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            if (!event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {

                PrivateChannel Skidder = event.getJDA().getUserById("477141528981012511").openPrivateChannel().complete();
                PrivateChannel Hax = event.getJDA().getUserById("261083609148948488").openPrivateChannel().complete();

                if (event.getAuthor().getAvatarUrl() == null) {
                    EmbedBuilder message = new EmbedBuilder().setTitle("New DM by " + event.getAuthor().getAsTag()).setAuthor(event.getAuthor().getName(), event.getAuthor().getDefaultAvatarUrl(), event.getAuthor().getDefaultAvatarUrl()).setDescription(event.getMessage().getContentRaw()).setTimestamp(Instant.now());

                    Skidder.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, message).build()).queue();
                } else {
                    EmbedBuilder message = new EmbedBuilder().setTitle("New DM by " + event.getAuthor().getAsTag()).setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl()).setDescription(event.getMessage().getContentRaw()).setTimestamp(Instant.now());

                    Skidder.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, message).build()).queue();
                }
            }
        }
    }
}
