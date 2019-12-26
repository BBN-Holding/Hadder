package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@Perms(Perm.MANAGE_SERVER)
public class RulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
            event.getTextChannel().sendMessage(
                    event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.moderation.rules.setup.title",
                            "commands.moderation.rules.setup.description")
                            .build()).queue();
            event.getEventWaiter().newOnMessageEventWaiter(event1 -> {
                if (event1.getMessage().getMentionedChannels().size() == 1) {
                    try {
                        TextChannel channel = event1.getMessage().getMentionedChannels().get(0);
                        createRules(event, event1, channel);
                    } catch (Exception e) {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                "commands.moderation.rules.channel.error.title",
                                "commands.moderation.rules.channel.error.description")
                                .build()).queue();
                    }
                } else {
                    try {
                        TextChannel channel = event1.getGuild().getTextChannelsByName(event1.getMessage().getContentRaw(), true).get(0);
                        createRules(event, event1, channel);
                    } catch (Exception e) {
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.ERROR,
                                        "commands.moderation.rules.channel.error.title",
                                        "commands.moderation.rules.channel.error.description")
                                        .build()).queue();
                    }
                }
            }, event);
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        }
    }

    public void createRules(CommandEvent event, GuildMessageReceivedEvent event1, TextChannel channel) {
        if (channel.getGuild().getId().equals(event1.getGuild().getId())) {
            if (event.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE)) {
                event1.getChannel().sendMessage(
                        event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.rules.rules.title",
                                "",
                                "commands.moderation.rules.rules.description",
                                channel.getName())
                        .build()).queue();
                event.getEventWaiter().newOnMessageEventWaiter(event2 -> {
                    String message = event2.getMessage().getContentRaw();
                    event2.getChannel().sendMessage(
                            event.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.INFO,
                                    "commands.moderation.rules.role.title",
                                    "commands.moderation.rules.role.description")
                            .build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(event3 -> {
                        if (event3.getMessage().getMentionedRoles().size() == 1) {
                            Role role = event3.getMessage().getMentionedRoles().get(0);
                            setRole(event, channel, message, event3, role);
                        } else if (event3.getGuild().getRolesByName(event3.getMessage().getContentStripped(), true).size() > 0) {
                                Role role = event3.getGuild().getRolesByName(event3.getMessage().getContentStripped(), true).get(0);
                                setRole(event, channel, message, event3, role);
                        } else {
                            event3.getChannel().sendMessage(
                                    event.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.WARNING,
                                            "commands.moderation.rules.role.error.title",
                                            "commands.moderation.rules.role.error.description")
                                    .build()).queue();
                        }
                    }, event);
                }, event);
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.moderation.rules.error.message.title",
                        "commands.moderation.rules.error.message.description")
                        .build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(
                    event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.WARNING,
                            "commands.moderation.rules.guild.error.title",
                            "commands.moderation.rules.guild.error.description")
                    .build()).queue();
        }
    }

    public void setRole(CommandEvent event, TextChannel channel, String message, GuildMessageReceivedEvent event3, Role role) {
        if (event3.getGuild().getSelfMember().canInteract(role)) {
            event3.getChannel().sendMessage(
                    event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.moderation.rules.emote.accept.title",
                            "",
                            "commands.moderation.rules.emote.accept.description", role.getName())
                    .build()).queue();
            event.getEventWaiter().newOnMessageEventWaiter(event4 -> {
                if (event4.getMessage().getEmotes().size() == 1) {
                    Emote aemote = event4.getMessage().getEmotes().get(0);
                    event4.getChannel().sendMessage(
                            event.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.INFO,
                                    "commands.moderation.rules.emote.decline.title", "",
                                    "commands.moderation.rules.emote.decline.title", String.valueOf(aemote))
                            .build()).queue();
                    event.getEventWaiter().newOnMessageEventWaiter(event5 -> {
                        Emote demote = event5.getMessage().getEmotes().get(0);
                        if (!aemote.equals(demote)) {
                            Message rules = channel.sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                    .setTitle("Rules")
                                    .setDescription(message)
                                    .build()).complete();
                            try {
                                rules.addReaction(aemote).queue();
                                rules.addReaction(demote).queue();
                                event5.getChannel().sendMessage(
                                        event.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.INFO,
                                                "commands.moderation.rules.success.title",
                                                "",
                                                "commands.moderation.rules.success.description",
                                                channel.getAsMention())
                                        .build()).queue();
                            } catch (Exception e) {
                                event5.getChannel().sendMessage(
                                        event.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.ERROR,
                                                "error",
                                                "commands.moderation.rules.emote.error.access.description")
                                        .build()).queue();
                                e.printStackTrace();
                            }
                            event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId(), aemote.toString(), demote.toString());
                        } else {
                            event.getTextChannel().sendMessage(
                                    event.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.WARNING,
                                            "commands.moderation.rules.emote.error.equal.title",
                                            "commands.moderation.rules.emote.error.equal.description")
                                    .build()).queue();
                        }
                    }, event);
                } else {
                    String aemote = event4.getMessage().getContentRaw();
                    event4.getChannel().sendMessage(
                            event.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.INFO,
                                    "commands.moderation.rules.emote.decline.title",
                                    "commands.moderation.rules.emoji.decline.description")
                            .build()).queue();
                    event.getEventWaiter().newOnMessageEventWaiter(event5 -> {
                        String demote = event5.getMessage().getContentRaw();
                        if (!aemote.equals(demote)) {
                            Message rules = channel.sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                    .setTitle("Rules")
                                    .setDescription(message)
                                    .build()).complete();
                            try {
                                rules.addReaction(aemote).queue();
                                rules.addReaction(demote).queue();
                                event5.getChannel().sendMessage(
                                        event.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.INFO,
                                                "commands.moderation.rules.success.title",
                                                "",
                                                "commands.moderation.rules.success.description",
                                                channel.getAsMention())
                                        .build()).queue();
                            } catch (Exception e) {
                                event5.getChannel().sendMessage(
                                        event.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.ERROR,
                                                "error",
                                                "commands.moderation.rules.emoji.error.description")
                                        .build()).queue();
                                e.printStackTrace();
                            }
                            event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), role.getId(), aemote, demote);
                        } else {
                            event.getTextChannel().sendMessage(
                                    event.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.WARNING,
                                            "commands.moderation.rules.emote.error.equal.title",
                                            "commands.moderation.rules.emote.error.equal.description")
                                    .build()).queue();
                        }
                    }, event
                    );
                }
            }, event);
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"rules", "rule", "setup"};
    }

    @Override
    public String description() {
        return "commands.moderation.rules.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
