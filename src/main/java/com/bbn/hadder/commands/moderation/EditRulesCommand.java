package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author Skidder / GregTCLTK
 */

@Perms(Perm.MANAGE_SERVER)
public class EditRulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getRethink().getRulesMID(event.getGuild().getId()).length() == 18) {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.moderation.editrules.message.title",
                    "commands.moderation.editrules.message.description").build()).queue();

            event.getEventWaiter().newOnMessageEventWaiter(event1 -> {
                String rules = event1.getMessage().getContentRaw();
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.moderation.editrules.channel.title",
                        "commands.moderation.editrules.channel.description").build()).queue();

                event.getEventWaiter().newOnMessageEventWaiter(event2 -> {
                    if (event2.getMessage().getMentionedChannels().size() == 1) {
                        try {
                            TextChannel channel = event2.getMessage().getMentionedChannels().get(0);
                            checkChannel(event, rules, channel);
                        } catch (Exception e) {
                            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                    "commands.moderation.editrules.channel.error.title",
                                    "commands.moderation.editrules.channel.error.description")
                                    .build()).queue();
                        }
                    } else {
                        try {
                            TextChannel channel = event1.getGuild().getTextChannelsByName(event2.getMessage().getContentRaw(), true).get(0);
                            checkChannel(event, rules, channel);
                        } catch (Exception e) {
                            event.getTextChannel().sendMessage(
                                    event.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.ERROR,
                                            "commands.moderation.editrules.channel.error.title",
                                            "commands.moderation.editrules.channel.error.description")
                                            .build()).queue();
                        }
                    }
                }, event);
            }, event);
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.editrules.error.title", "",
                    "commands.moderation.editrules.error.description", event.getRethink().getGuildPrefix(event.getGuild().getId())).build()).queue();
        }
    }

    public void checkChannel(CommandEvent event, String rules, TextChannel channel) {
        try {
            channel.retrieveMessageById(event.getRethink().getRulesMID(event.getGuild().getId())).queue();
            setRules(event, rules, channel);
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.moderation.editrules.success.title",
                    "commands.moderation.editrules.success.description").build()).queue();
        } catch (Exception e) {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.editrules.channel.message.error.title",
                    "commands.moderation.editrules.channel.message.error.description").build()).queue();
        }
    }

    public void setRules(CommandEvent event, String rules, TextChannel channel) {
        channel.retrieveMessageById(event.getRethink().getRulesMID(event.getGuild().getId())).complete().editMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Rules")
                .setDescription(rules)
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"editrules", "rulesedit", "edit_rules", "rules_edit"};
    }

    @Override
    public String description() {
        return "commands.moderation.editrules.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
