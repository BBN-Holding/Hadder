package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                        .setTitle("Set up rules")
                        .setDescription("Welcome to the Hadder rules setup. Please mention the channel in which I should send the rules. Your message should look like: #rules or #verify.")
                        .build()).queue();
                new EventWaiter().newOnMessageEventWaiter(event1 -> {
                    if (event1.getMessage().getMentionedChannels().size() == 1) {
                        try {
                            TextChannel channel = event1.getMessage().getMentionedChannels().get(0);
                            createRules(event, event1, channel);
                        } catch (Exception e) {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                    .setTitle("Channel not found")
                                    .setDescription("I can't find the specified channel. Please start the setup again.")
                                    .build()).queue();
                        }
                    } else {
                        try {
                            TextChannel channel = event1.getGuild().getTextChannelsByName(event1.getMessage().getContentRaw(), true).get(0);
                            createRules(event, event1, channel);
                        } catch (Exception e) {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                    .setTitle("Channel not found")
                                    .setDescription("I can't find the specified channel. Please start the setup again.")
                                    .build()).queue();
                        }
                    }
                    }, event.getJDA(), event.getAuthor());
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
        }
    }

    public void createRules(CommandEvent event, GuildMessageReceivedEvent event1, TextChannel channel) {
        if (channel.getGuild().getId().equals(event1.getGuild().getId())) {
            if (event.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE)) {
                event1.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                        .setTitle("Rules")
                        .setDescription("The channel was successfully set to " + channel.getName() + ". Please send me the rules now.")
                        .build()).queue();
                new EventWaiter().newOnMessageEventWaiter(event2 -> {
                    String message = event2.getMessage().getContentRaw();
                    event2.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle("Role to assign")
                            .setDescription("The rules were successfully set. Please send me the name of the role which the user receives after he accepted the rules.")
                            .build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(event3 -> {
                        if (event3.getMessage().getMentionedRoles().size() == 1) {
                            Role role = event3.getMessage().getMentionedRoles().get(0);
                            setRole(event, channel, message, event3, role);
                        } else if (event3.getGuild().getRolesByName(event3.getMessage().getContentStripped(), true).size() > 0) {
                                Role role = event3.getGuild().getRolesByName(event3.getMessage().getContentStripped(), true).get(0);
                                setRole(event, channel, message, event3, role);
                        } else {
                            event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                    .setTitle("Role does not exist")
                                    .setDescription("The specified role does not exist on this guild.")
                                    .build()).queue();
                        }
                    }, event.getJDA(), event.getAuthor());
                }, event.getJDA(), event.getAuthor());
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION)
                        .build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                    .setTitle("Wrong Guild")
                    .setDescription("The mentioned channel must be on this guid!")
                    .build()).queue();
        }
    }

    public void setRole(CommandEvent event, TextChannel channel, String message, GuildMessageReceivedEvent event3, Role role) {
        if (event3.getGuild().getSelfMember().canInteract(role)) {
            event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                    .setTitle("Custom Accept Emote")
                    .setDescription("The role has been successfully set to " + role.getName() + ". Now send me the emote on which your user should react to to get verified.")
                    .build()).queue();
            new EventWaiter().newOnMessageEventWaiter(event4 -> {
                if (event4.getMessage().getEmotes().size() == 1) {
                    Emote aemote = event4.getMessage().getEmotes().get(0);
                    event4.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle("Custom Decline Emote")
                            .setDescription("The first emote has been successfully set to " + aemote + ". Please send me now the decline emote.")
                            .build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(event5 -> {
                        Emote demote = event5.getMessage().getEmotes().get(0);
                        if (!aemote.equals(demote)) {
                            Message rules = channel.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                    .setTitle("Rules")
                                    .setDescription(message)
                                    .build()).complete();
                            try {
                                rules.addReaction(aemote).queue();
                                rules.addReaction(demote).queue();
                                event5.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle("Successfully set the rules")
                                        .setDescription("I successfully send the rules in " + channel.getAsMention() + ".")
                                        .build()).queue();
                            } catch (Exception e) {
                                event5.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                        .setTitle("Error")
                                        .setDescription("I can not access the custom emote(s),")
                                        .build()).queue();
                                e.printStackTrace();
                            }
                            event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId(), aemote.toString(), demote.toString());
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                    .setTitle("Emotes are equal")
                                    .setDescription("The 1st and 2nd emote equals each other.")
                                    .build()).queue();
                        }
                    }, event.getJDA(), event.getAuthor());
                } else {
                    String aemote = event4.getMessage().getContentRaw();
                    event4.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle("Custom Decline Emote")
                            .setDescription("The first emote has been successfully set. Please send me now the decline emote.")
                            .build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(event5 -> {
                        String demote = event5.getMessage().getContentRaw();
                        if (!aemote.equals(demote)) {
                            Message rules = channel.sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                    .setTitle("Rules")
                                    .setDescription(message)
                                    .build()).complete();
                            try {
                                rules.addReaction(aemote).queue();
                                rules.addReaction(demote).queue();
                                event5.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle("Successfully set the rules")
                                        .setDescription("I successfully send the rules in " + channel.getAsMention() + ".")
                                        .build()).queue();
                            } catch (Exception e) {
                                event5.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                        .setTitle("Error")
                                        .setDescription("The given emote can't be used.")
                                        .build()).queue();
                                e.printStackTrace();
                            }
                            event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId(), aemote, demote);
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                    .setTitle("Emotes are equal")
                                    .setDescription("The 1st and 2nd emote equals each other.")
                                    .build()).queue();
                        }
                    }, event.getJDA(), event.getAuthor());
                }
            }, event.getJDA(), event.getAuthor());
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"rules", "rule", "setup"};
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
