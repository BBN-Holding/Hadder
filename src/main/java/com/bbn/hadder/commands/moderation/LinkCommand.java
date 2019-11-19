package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;

public class LinkCommand implements Command {
    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length==0) {

        } else {
            switch (args[0].toLowerCase()) {
                case "add":
                    if (args.length==2) {
                        boolean found = false;
                        for (Guild g :event.getJDA().getGuilds()) {
                            if (g.getId().equals(args[1])) found = true;
                        }
                        if (!found) {
                            event.getChannel().sendMessage(
                                new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR, new EmbedBuilder()
                                        .setTitle("I'm not on this guild")
                                        .setDescription("I'm not on this guild. Please tell the Server Owner to add me.")).build()).queue();
                            return;
                        }
                        JSONArray linkedguildids = event.getRethink().getLinks(event.getGuild().getId());
                        for (int i = 0; linkedguildids.length() > i; i++) {
                            if (linkedguildids.getString(i).equals(args[1])) {
                                event.getChannel().sendMessage(
                                        new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR, new EmbedBuilder()
                                                .setTitle("Already Linked with that Guild!")
                                                .setDescription("Your Guild is already linked with " + event.getJDA().getGuildById(args[1]).getName())).build()).queue();
                                return;
                            }
                        }

                        // TODO: Fix this
                        // TODO: Add poll to accept
                        for (int i = 0; linkedguildids.length() > i; i++) {
                            // TODO: Check if any channel exists else create
                            event.getJDA().getTextChannelById(event.getRethink().getLinkChannel(linkedguildids.getString(i))).sendMessage(
                                    new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                    .setTitle("A Guild wants to link the Guilds")
                                    .setDescription("The Guild "+event.getGuild().getName()+" want to link!")
                                    .setFooter("Request by: "+event.getGuild().getId()+ " To:"+linkedguildids.getString(i))).build()).queue(
                                            msg -> {
                                                msg.addReaction("✅").queue();
                                                msg.addReaction("❌").queue();
                                            }
                            );
                        }
                        // TODO: Success Message
                    }
                    break;

                case "remove":

                    break;

                case "list":
                    JSONArray linkedguildids = event.getRethink().getLinks(event.getGuild().getId());
                    String response;
                    if (linkedguildids.length()==0) response = "There are no Guilds linked.";
                    else {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; linkedguildids.length()>i; i++) {
                            String id = linkedguildids.getString(i);
                            Guild guild = event.getJDA().getGuildById(id);
                            sb.append(guild.getName()+" ("+id+")\n");
                        }
                        response = sb.toString();
                    }

                    event.getChannel().sendMessage(
                            new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO,
                                    new EmbedBuilder().setTitle("Linked Guilds").setDescription(response)).build()).queue();
                    break;

                case "channel":
                    if (args.length==2) {
                        for (TextChannel tc : event.getJDA().getTextChannels()) {
                            if (tc.getId().equals(args[1])) {
                                event.getRethink().setLinkChannel(event.getGuild().getId(), args[1]);
                                event.getChannel().sendMessage(
                                        new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                .setTitle("Successfully set the text channel as your link channel")
                                                .setDescription("The new Channel receives all new messages")).build()).queue();
                            }
                        }

                    }
                    break;
            }
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
