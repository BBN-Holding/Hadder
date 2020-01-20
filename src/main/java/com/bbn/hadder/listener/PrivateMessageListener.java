package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class PrivateMessageListener extends ListenerAdapter {

    private Rethink rethink;

    public PrivateMessageListener(Rethink rethink) {
        this.rethink = rethink;
    }

    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromType(ChannelType.PRIVATE) && !e.getAuthor().getId().equals(e.getJDA().getSelfUser().getId())) {
            PrivateChannel Skidder = e.getJDA().getUserById("477141528981012511").openPrivateChannel().complete();
            PrivateChannel Hax = e.getJDA().getUserById("261083609148948488").openPrivateChannel().complete();

            Skidder.sendMessage(new MessageEditor(rethink, e.getJDA().getUserById("261083609148948488")).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + e.getAuthor().getAsTag())
                    .setAuthor(e.getAuthor().getName(), e.getAuthor().getAvatarUrl(), e.getAuthor().getAvatarUrl())
                    .setDescription(e.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();
            Hax.sendMessage(new MessageEditor(rethink, e.getJDA().getUserById("261083609148948488")).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + e.getAuthor().getAsTag())
                    .setAuthor(e.getAuthor().getName(), e.getAuthor().getAvatarUrl(), e.getAuthor().getAvatarUrl())
                    .setDescription(e.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();
        }
    }
}
