package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Random;

public class MentionListener extends ListenerAdapter {

    private Rethink rethink;


    public MentionListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && (event.getMessage().getContentRaw().equals(event.getGuild().getSelfMember().getAsMention())||
                event.getMessage().getContentRaw().equals(event.getGuild().getSelfMember().getAsMention().replace("@", "@!")))) {
            event.getChannel().sendMessage(new MessageEditor(rethink, event.getAuthor()).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("Hello I'm Hadder.")
                    .setAuthor(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                    .addField("Users", String.valueOf(event.getJDA().getUsers().size()), false)
                    .addField("Guilds", String.valueOf(event.getJDA().getGuilds().size()), false)
                    .addField("Prefix (User)", rethink.getUserPrefix(event.getAuthor().getId()), false)
                    .addField("Prefix (Guild)", rethink.getGuildPrefix(event.getGuild().getId()), false)
                    .build()).queue();
        }
    }
}
