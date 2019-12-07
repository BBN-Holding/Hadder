package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

public class LinkCommand implements Command {
    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 0) event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length != 2) return;
                String linkid = args[1];

                // Check if guild exists
                boolean found = false;
                Guild linkguild = null;
                for (Guild g : event.getJDA().getGuilds()) {
                    if (g.getId().equals(linkid)) {
                        found = true;
                        linkguild = g;
                    }
                }
                if (!found) {
                    event.getChannel().sendMessage(
                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                    .setTitle("Success!").setDescription("If i'm on this guild i sent a message to accept the link.")
                            .build()).queue();
                    return;
                }

                // Check if the guild is the same
                if (event.getGuild().getId().equals(args[1])) {
                    event.getChannel().sendMessage(
                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                    .setTitle("Wait that's illegal.").setDescription("You specified the same guild as the guild on which you're reading this").build()).queue();
                    return;
                }

                // Send poll to all connected guilds
                Guild finalLinkguild = linkguild;
                new Thread(() -> {
                    if (event.getRethink().getLinkChannel(linkid).isEmpty()) {
                        event.getRethink().setLinkChannel(linkid, finalLinkguild.createTextChannel("linkchannel").complete().getId());
                    }
                    // Send Request to link Guild
                    event.getJDA().getTextChannelById(event.getRethink().getLinkChannel(linkid)).sendMessage(
                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                    .setTitle(event.getGuild().getName() + " (" + event.getGuild().getId() + ") wants to link guilds!")
                                            .setDescription("React with the reactions to accept or decline it").build()
                    ).queue(
                            msg -> {
                                msg.addReaction("✅").queue();
                                msg.addReaction("❌").queue();
                            }
                    );
                }).start();


                event.getChannel().sendMessage(
                        new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                .setTitle("Success!").setDescription("If i'm on this guild i sent a message to accept the link.")
                        .build()).queue();

                break;

            case "channel":
                if (args.length == 2) {
                    event.getRethink().setLinkChannel(event.getGuild().getId(), args[1]);
                    event.getChannel().sendMessage(new EmbedBuilder().setTitle("Success").setDescription("Set the thing boi").build()).queue();
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
