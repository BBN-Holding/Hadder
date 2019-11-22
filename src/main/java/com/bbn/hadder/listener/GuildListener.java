package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.Config;
import com.bbn.hadder.utils.BotList;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class GuildListener extends ListenerAdapter {

    private Rethink rethink;
    private Config config;

    public GuildListener(Rethink rethink, Config config) {
        this.rethink = rethink;
        this.config = config;
    }

    public void onGuildJoin(GuildJoinEvent event) {
        new Thread(() -> {
            for (Member member : event.getGuild().getMembers()) {
                if (!member.getUser().getId().equals(event.getJDA().getSelfUser().getId())) {
                    rethink.insertUser(member.getUser().getId());
                }
            }
        }).start();

        rethink.insertGuild(event.getGuild().getId());
        EmbedBuilder builder = new EmbedBuilder();
        event.getJDA().getTextChannelById("475722540140986369").sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder)
                .setTitle("Joined Server")
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Name", event.getGuild().getName(), true)
                .addField("Guild ID", event.getGuild().getId(), true)
                .addField("Guild Owner", event.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("Users", String.valueOf(event.getGuild().getMembers().size()), true)
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build()).queue();

        new BotList(config).post();
    }

    public void onGuildLeave(GuildLeaveEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        event.getJDA().getTextChannelById("475722540140986369").sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder)
                .setTitle("Left Server")
                .setThumbnail(event.getGuild().getIconUrl())
                .addField("Name", event.getGuild().getName(), true)
                .addField("Guild ID", event.getGuild().getId(), true)
                .addField("Guild Owner", event.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("Users", String.valueOf(event.getGuild().getMembers().size()), true)
                .setFooter(event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build()).queue();

        new BotList(config).post();
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (!event.getUser().getId().equals(event.getJDA().getSelfUser().getId())) {
            rethink.insertUser(event.getUser().getId());
        }
    }
}
