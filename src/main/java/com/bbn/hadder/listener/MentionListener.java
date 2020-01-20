package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.RethinkServer;
import com.bbn.hadder.RethinkUser;
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
        if (!event.getAuthor().isBot()) {
            RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", event.getGuild().getId()), rethink);
            RethinkUser rethinkUser = new RethinkUser(rethink.getObjectByID("user", event.getAuthor().getId()), rethink);
            if (event.isFromType(ChannelType.TEXT) && (event.getMessage().getContentRaw().equals(event.getGuild().getSelfMember().getAsMention()) ||
                    event.getMessage().getContentRaw().equals(event.getGuild().getSelfMember().getAsMention().replace("@", "@!")))) {
                event.getChannel().sendMessage(new MessageEditor(rethinkUser, event.getAuthor()).getMessage(MessageEditor.MessageType.INFO)
                        .setTitle("Hello I'm Hadder.")
                        .setAuthor(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl(), event.getJDA().getSelfUser().getAvatarUrl())
                        .addField("Users", String.valueOf(event.getJDA().getUsers().size()), false)
                        .addField("Guilds", String.valueOf(event.getJDA().getGuilds().size()), false)
                        .addField("Prefix (User)", rethinkUser.getPrefix(), false)
                        .addField("Prefix (Guild)", rethinkServer.getPrefix(), false)
                        .build()).queue();
            } else if (event.getMessage().getContentRaw().equalsIgnoreCase("@someone")) {
                int member = new Random().nextInt(event.getGuild().getMembers().size() - 1);
                if (member > 0 && member < event.getGuild().getMembers().size()) {
                    event.getChannel().sendMessage(event.getGuild().getMembers().get(member).getAsMention() + " (Executed by: " + event.getAuthor().getAsTag() + ")").queue();
                }
            }
        }
    }
}
