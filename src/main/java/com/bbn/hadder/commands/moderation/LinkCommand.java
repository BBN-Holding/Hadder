package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

public class LinkCommand implements Command {
    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 0) event.getHelpCommand().sendHelp(this, event);
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
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "success!")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.request.success.description"))
                            .build()).queue();
                    return;
                }

                // Check if the guild is the same
                if (event.getGuild().getId().equals(args[1])) {
                    event.getChannel().sendMessage(
                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.error.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.request.error.description")).build()).queue();
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
                                    .setTitle(event.getGuild().getName() + " (" + event.getGuild().getId() + MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.request.accept.title"))
                                            .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.request.accept.description")).build()
                    ).queue(
                            msg -> {
                                msg.addReaction("✅").queue();
                                msg.addReaction("❌").queue();
                            }
                    );
                }).start();


                event.getChannel().sendMessage(
                        new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "success!")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.request.success.description"))
                        .build()).queue();

                break;

            case "channel":
                if (args.length == 2) {
                    event.getRethink().setLinkChannel(event.getGuild().getId(), args[1]);
                    event.getChannel().sendMessage(new EmbedBuilder().setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "success!")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.link.set.title")).build()).queue();
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
        return MessageEditor.handle("en", "commands.moderation.link.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
