package com.bbn.hadder.commands.fun;

/*
 * @author Skidder / GregTCLTK
 */

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.commands.Perm;
import com.bbn.hadder.commands.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Perms(Perm.MANAGE_SERVER)
public class ClydeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
                TextChannel channel = event.getMessage().getTextChannel();
                String content = event.getMessage().getContentRaw().replace(event.getRethink().getGuildPrefix(event.getGuild().getId()), "").replace(event.getRethink().getUserPrefix(event.getAuthor().getId()), "").replace("clyde", "");

                Webhook webhook = channel.createWebhook(event.getConfig().getClydeName()).complete();
                try {
                    InputStream s = new URL("https://discordapp.com/assets/f78426a064bc9dd24847519259bc42af.png").openStream();
                    webhook.getManager().setAvatar(Icon.from(s)).queue();

                    WebhookClientBuilder builder = new WebhookClientBuilder(webhook.getUrl());

                    WebhookClient client = builder.build();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    client.send(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                webhook.delete().queue();
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else event.getHelpCommand().sendHelp(this, event);

    }

    @Override
    public String[] labels() {
        return new String[]{"clyde"};
    }

    @Override
    public String description() {
        return "Send a message as a Clyde webhook.";
    }

    @Override
    public String usage() {
        return "<Message-Content>";
    }
}
