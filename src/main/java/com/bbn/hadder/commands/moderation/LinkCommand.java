package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.LinkUtils;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkCommand implements Command {
    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 0) return;
        switch (args[0]) {

            case "add":
                if (args.length != 2) return;
                String linkid = args[1];

                // Check if guild is existing
                boolean found = false;
                Guild linkguild = null;
                for (Guild g : event.getJDA().getGuilds()) {
                    if (g.getId().equals(linkid)) {
                        found = true;
                        linkguild = g;
                    }
                }
                // TODO
                if (!found) return;

                // Check if the guild is the same
                if (event.getGuild().getId().equals(args[1])) {
                    event.getChannel().sendMessage(
                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR,
                                    new EmbedBuilder().setTitle("Wait thats illegal.").setDescription("You specified the same guild as the guild on which you're reading this")).build()).queue();
                    return;
                }

                // Send poll to all connected guilds
                if (event.getRethink().getLinks(event.getGuild().getId()).length() > 0) {
                    List<String> reactions = new ArrayList<>();
                    reactions.addAll(Arrays.asList("✅", "❌"));
                    new LinkUtils().sendAll(
                            event.getRethink().getLinks(event.getGuild().getId()), event.getJDA(),
                            new MessageBuilder()
                                    .setEmbed(
                                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO,
                                                    new EmbedBuilder()
                                                            .setTitle(event.getGuild().getName() + " (" + event.getGuild().getId() + ") wants to add " + linkguild.getName() + " (" + linkguild.getId() + ")")
                                                            .setDescription("Please vote if the Guild should be added.")).build()).build(),
                            event.getJDA().getSelfUser(), reactions);
                } else {
                    Guild finalLinkguild = linkguild;
                    new Thread(() -> {
                        if (event.getRethink().getLinkChannel(linkid).isEmpty()) {
                            event.getRethink().setLinkChannel(linkid, finalLinkguild.createTextChannel("linkchannel").complete().getId());
                        }
                        // Send Request to link Guild
                        event.getJDA().getTextChannelById(event.getRethink().getLinkChannel(linkid)).sendMessage(
                                new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO,
                                        new EmbedBuilder().setTitle(event.getGuild().getName() + " (" + event.getGuild().getId() + ") wants to link guilds!")
                                                .setDescription("React with the reactions to accept or decline it")).build()
                        ).queue(
                                msg -> {
                                    msg.addReaction("✅").queue();
                                    msg.addReaction("❌").queue();
                                }
                        );
                    }).start();
                }

                break;
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"link"};
    }

    @Override
    public String description() {
        return "Links two or more servers.";
    }

    @Override
    public String usage() {
        return "";
    }
}
