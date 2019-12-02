package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class PrivateMessageListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            PrivateChannel Skidder = event.getJDA().getUserById("477141528981012511").openPrivateChannel().complete();
            PrivateChannel Hax = event.getJDA().getUserById("261083609148948488").openPrivateChannel().complete();

            Skidder.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + event.getAuthor().getAsTag())
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                    .setDescription(event.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();
            Hax.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + event.getAuthor().getAsTag())
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                    .setDescription(event.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();
        }
    }
}
