package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {

    public void onGuildJoin(GuildJoinEvent event) {
        Rethink.insertServer(event.getGuild().getId());
    }

    public void onGuildLeave(GuildLeaveEvent event) {

    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (!event.getUser().getId().equals(event.getJDA().getSelfUser().getId())) {
            Rethink.insertUser(event.getUser().getId());
        }
    }
}
