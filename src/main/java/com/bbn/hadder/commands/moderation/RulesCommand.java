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
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class RulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                        .setTitle("Set up rules")
                        .setDescription("Please specify the channel on which I should send the rules. Your message should look like: #rules."))
                        .build()).queue();
                new EventWaiter().newOnMessageEventWaiter(event1 -> {
                    if (event1.getMessage().getMentionedChannels().size() == 1) {
                        TextChannel channel = event1.getMessage().getMentionedChannels().get(0);
                        if (channel.getGuild().getId().equals(event1.getGuild().getId())) {
                            if (event.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE)) {
                                event1.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                        .setTitle("Rules")
                                        .setDescription("The channel was successfully set to " + channel.getName() + ". Please send me the rules now."))
                                        .build()).queue();
                                new EventWaiter().newOnMessageEventWaiter(event2 -> {
                                    String message = event2.getMessage().getContentDisplay();
                                    event2.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                            .setTitle("Role to assign"))
                                            .setDescription("The rules were successfully set. Please send me the name of the role which the user receives after he accepted the rules.")
                                            .build()).queue();
                                    new EventWaiter().newOnMessageEventWaiter(event3 -> {
                                        if (event.getMessage().getMentionedRoles().size() == 1) {
                                            Role role = event.getMessage().getMentionedRoles().get(0);
                                            if (event3.getGuild().getSelfMember().canInteract(role)) {
                                                event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("The role has been successfully set to " + role.getName() + "."))
                                                        .build()).queue();
                                                Message rules = channel.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("Rules"))
                                                        .setDescription(message)
                                                        .build()).complete();
                                                rules.addReaction("✅").queue();
                                                rules.addReaction("❌").queue();
                                                event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId());
                                            } else {
                                                EmbedBuilder builder = new EmbedBuilder();
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                            }
                                        } else {
                                            Role role = event3.getGuild().getRolesByName(event3.getMessage().getContentRaw().replace("h.rules ", ""), true).get(0);
                                            if (event3.getGuild().getSelfMember().canInteract(role)) {
                                                event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("The role has been successfully set to " + role.getName() + "."))
                                                        .build()).queue();
                                                Message rules = channel.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("Rules"))
                                                        .setDescription(message)
                                                        .build()).complete();
                                                rules.addReaction("✅").queue();
                                                rules.addReaction("❌").queue();
                                                event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId());
                                            } else {
                                                EmbedBuilder builder = new EmbedBuilder();
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                            }
                                        }
                                    }, event.getJDA(), event.getAuthor());
                                }, event.getJDA(), event.getAuthor());
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, new EmbedBuilder()
                                        .setTitle("Wrong Guild")
                                        .setDescription("The mentioned channel must be on this guid!"))
                                        .build()).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder)
                                    .build()).queue();
                        }
                    } else {
                        TextChannel channel = event1.getGuild().getTextChannelsByName(event1.getMessage().getContentRaw(), true).get(0);
                        if (channel.getGuild().getId().equals(event1.getGuild().getId())) {
                            if (event.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE)) {
                                event1.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                        .setTitle("Rules")
                                        .setDescription("The channel was successfully set to " + channel.getName() + ". Please send me the rules now."))
                                        .build()).queue();
                                new EventWaiter().newOnMessageEventWaiter(event2 -> {
                                    String message = event2.getMessage().getContentDisplay();
                                    event2.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                            .setTitle("Role to assign"))
                                            .setDescription("The rules were successfully set. Please send me the name of the role which the user receives after he accepted the rules.")
                                            .build()).queue();
                                    new EventWaiter().newOnMessageEventWaiter(event3 -> {
                                        if (event.getMessage().getMentionedRoles().size() == 1) {
                                            Role role = event.getMessage().getMentionedRoles().get(0);
                                            if (event3.getGuild().getSelfMember().canInteract(role)) {
                                                event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("The role has been successfully set to " + role.getName() + "."))
                                                        .build()).queue();
                                                Message rules = channel.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("Rules"))
                                                        .setDescription(message)
                                                        .build()).complete();
                                                rules.addReaction("✅").queue();
                                                rules.addReaction("❌").queue();
                                                event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId());
                                            } else {
                                                EmbedBuilder builder = new EmbedBuilder();
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                            }
                                        } else {
                                            Role role = event3.getGuild().getRolesByName(event3.getMessage().getContentRaw(), true).get(0);
                                            if (event3.getGuild().getSelfMember().canInteract(role)) {
                                                event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("The role has been successfully set to " + role.getName() + "."))
                                                        .build()).queue();
                                                Message rules = channel.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                                        .setTitle("Rules"))
                                                        .setDescription(message)
                                                        .build()).complete();
                                                rules.addReaction("✅").queue();
                                                rules.addReaction("❌").queue();
                                                event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId());
                                            } else {
                                                EmbedBuilder builder = new EmbedBuilder();
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                            }
                                        }
                                    }, event.getJDA(), event.getAuthor());
                                }, event.getJDA(), event.getAuthor());
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder)
                                        .build()).queue();
                            }
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, new EmbedBuilder()
                                        .setTitle("Wrong Guild")
                                        .setDescription("The mentioned channel must be on this guid!"))
                                        .build()).queue();
                            }
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
        return new String[]{"rules", "rule"};
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
