package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MentionListener extends ListenerAdapter {

    private Rethink rethink;

    public MentionListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getMessage().getContentRaw().equals(event.getGuild().getSelfMember().getAsMention())) {
                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("Hello I'm Hadder.")
                        .setAuthor(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                        .addField("Users", String.valueOf(event.getJDA().getUsers().size()), false)
                        .addField("Guilds", String.valueOf(event.getJDA().getGuilds().size()), false)
                        .addField("Prefix (User)", rethink.getUserPrefix(event.getAuthor().getId()), false)
                        .addField("Prefix (Guild)", rethink.getGuildPrefix(event.getGuild().getId()), false);
                event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).build()).queue();
            }
        }
    }
}
