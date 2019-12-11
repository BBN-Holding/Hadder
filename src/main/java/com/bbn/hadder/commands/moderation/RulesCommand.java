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
                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.setup.title"))
                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.setup.description"))
                        .build()).queue();
                new EventWaiter().newOnMessageEventWaiter(event1 -> {
                    if (event1.getMessage().getMentionedChannels().size() == 1) {
                        try {
                            TextChannel channel = event1.getMessage().getMentionedChannels().get(0);
                            createRules(event, event1, channel);
                        } catch (Exception e) {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.channel.error.title"))
                                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.channel.error.description"))
                                    .build()).queue();
                        }
                    } else {
                        try {
                            TextChannel channel = event1.getGuild().getTextChannelsByName(event1.getMessage().getContentRaw(), true).get(0);
                            createRules(event, event1, channel);
                        } catch (Exception e) {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.channel.error.title"))
                                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.channel.error.description"))
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
                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.rules.title"))
                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.rules.title", channel.getName()))
                        .build()).queue();
                new EventWaiter().newOnMessageEventWaiter(event2 -> {
                    String message = event2.getMessage().getContentRaw();
                    event2.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.role.title"))
                            .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.role.description"))
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
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.role.error.title"))
                                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.role.error.description"))
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
                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.guild.error.title"))
                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.guild.error.description"))
                    .build()).queue();
        }
    }

    public void setRole(CommandEvent event, TextChannel channel, String message, GuildMessageReceivedEvent event3, Role role) {
        if (event3.getGuild().getSelfMember().canInteract(role)) {
            event3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.accept.title"))
                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.accept.description", role.getName()))
                    .build()).queue();
            new EventWaiter().newOnMessageEventWaiter(event4 -> {
                if (event4.getMessage().getEmotes().size() == 1) {
                    Emote aemote = event4.getMessage().getEmotes().get(0);
                    event4.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.decline.title"))
                            .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.decline.title", String.valueOf(aemote)))
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
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.success.title"))
                                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.success.description", channel.getAsMention()))
                                        .build()).queue();
                            } catch (Exception e) {
                                event5.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "error"))
                                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.error.access.description"))
                                        .build()).queue();
                                e.printStackTrace();
                            }
                            event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId(), aemote.toString(), demote.toString());
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.error.equal.title"))
                                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.error.equal.description"))
                                    .build()).queue();
                        }
                    }, event.getJDA(), event.getAuthor());
                } else {
                    String aemote = event4.getMessage().getContentRaw();
                    event4.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.decline.title"))
                            .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emoji.decline.description"))
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
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.success.title"))
                                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.success.description", channel.getAsMention()))
                                        .build()).queue();
                            } catch (Exception e) {
                                event5.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.ERROR)
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "error"))
                                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emoji.error.description"))
                                        .build()).queue();
                                e.printStackTrace();
                            }
                            event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId(), aemote, demote);
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.error.equal.title"))
                                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.rules.emote.error.equal.description"))
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
        return MessageEditor.handle("en", "commands.moderation.rules.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
