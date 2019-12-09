package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.annotation.Nonnull;

public class InviteLinkListener extends ListenerAdapter {

    private Rethink rethink;

    public InviteLinkListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getMessage().getContentRaw().contains("discord.gg/")) {
                if (!event.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(event.getGuild().getId())) {
                    checkInvite(event.getMessage(), "discord.gg/");
                }
            } else if (event.getMessage().getContentRaw().contains("discordapp.com/invite")) {
                if ( !event.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(event.getGuild().getId())) {
                    checkInvite(event.getMessage(), "discordapp.com/invite/");
                }
            }
        }
    }

    public void checkInvite(Message message, String regex) {
        String split = message.getContentRaw().split(regex, 10)[1];
        String invite = split.split(" ")[0];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://canary.discordapp.com/api/v6/invites/" + invite).build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            if (!json.toString().contains("\"message\":")) {
                message.delete().reason("Invite Link detected").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getMessage().getContentRaw().contains("discord.gg/")) {
                if (!event.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(event.getGuild().getId())) {
                    checkInvite(event.getMessage(), "discord.gg/");
                }
            } else if (event.getMessage().getContentRaw().contains("discordapp.com/invite")) {
                if (!event.getMember().hasPermission(Permission.ADMINISTRATOR) && rethink.getInviteDetection(event.getGuild().getId())) {
                    checkInvite(event.getMessage(), "discordapp.com/invite/");
                }
            }
        }
    }
}
