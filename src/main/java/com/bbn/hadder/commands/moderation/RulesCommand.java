package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class RulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                        .setTitle("Set up rules")
                        .setDescription("Please specify the channel on which I should send the rules. Your message should look like: #rules."))
                        .build()).queue();

                new EventWaiter().newOnMessageEventWaiter(event1 -> {
                    if (event1.getMessage().getMentionedChannels().size() > 0) {
                        event1.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                .setTitle("Rules")
                                .setDescription("Please send me the rules now."))
                                .build()).queue();
                        new EventWaiter().newOnMessageEventWaiter(event2 -> {
                            event2.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                    .setTitle("Role to assign"))
                                    .setDescription("Please send me the name of the role the user receives after he has accepted the rules.")
                                    .build()).queue();
                            new EventWaiter().newOnMessageEventWaiter(event3 -> {
                                try {
                                    event3.getGuild().getRolesByName(event3.getMessage().getContentRaw(), true).get(0);
                                    event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder().setTitle("Successfully set the rules")).build()).queue();
                                    Message rules = event1.getMessage().getMentionedChannels().get(0).sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                            .setTitle("Rules"))
                                            .setDescription(event2.getMessage().getContentDisplay())
                                            .build()).complete();
                                    rules.addReaction("✅").queue();
                                    rules.addReaction("❌").queue();

                                    event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), event3.getGuild().getRolesByName(event3.getMessage().getContentRaw(), false).get(0).getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }, event.getJDA(), event.getAuthor());
                        }, event.getJDA(), event.getAuthor());
                    } else {
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, new EmbedBuilder()
                                .setTitle("No Channel mentioned"))
                                .setDescription("Please mention a channel. This should look like #rules")
                                .build()).queue();
                    }
                    }, event.getJDA(), event.getAuthor());
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"rules"};
    }

    @Override
    public String description() {
        return "Setup the rules on your Discord server";
    }

    @Override
    public String usage() {
        return "";
    }
}
