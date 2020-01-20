package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.Config;
import com.bbn.hadder.utils.BotList;
import com.bbn.hadder.utils.MessageEditor;
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

    public void onGuildJoin(GuildJoinEvent e) {
        new Thread(() -> {
            for (Member member : e.getGuild().getMembers()) {
                if (!member.getUser().getId().equals(e.getJDA().getSelfUser().getId())) {
                    rethink.insertUser(member.getUser().getId());
                }
            }
        }).start();

        rethink.insertGuild(e.getGuild().getId());
        e.getJDA().getTextChannelById("475722540140986369").sendMessage(new MessageEditor(null, null).getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Joined Server")
                .setThumbnail(e.getGuild().getIconUrl())
                .addField("Name", e.getGuild().getName(), true)
                .addField("Guild ID", e.getGuild().getId(), true)
                .addField("Guild Owner", e.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("Users", String.valueOf(e.getGuild().getMembers().size()), true)
                .setFooter(e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build()).queue();

        new BotList(config).post();
    }

    public void onGuildLeave(GuildLeaveEvent e) {
        e.getJDA().getTextChannelById("475722540140986369").sendMessage(new MessageEditor(null, null).getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Left Server")
                .setThumbnail(e.getGuild().getIconUrl())
                .addField("Name", e.getGuild().getName(), true)
                .addField("Guild ID", e.getGuild().getId(), true)
                .addField("Guild Owner", e.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("Users", String.valueOf(e.getGuild().getMembers().size()), true)
                .setFooter(e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build()).queue();

        new BotList(config).post();
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if (!e.getUser().getId().equals(e.getJDA().getSelfUser().getId())) {
            rethink.insertUser(e.getUser().getId());
        }
    }
}
