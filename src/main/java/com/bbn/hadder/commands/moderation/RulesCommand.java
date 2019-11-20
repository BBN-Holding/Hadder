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
                        .setTitle("Rules setup")
                        .setDescription("Please mention the channel în which I should send the rules. This should look like #rules."))
                        .build()).queue();

                new EventWaiter().newOnMessageEventWaiter(msgevent -> {
                    if (msgevent.getMessage().getMentionedChannels().size() > 0) {
                        msgevent.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                .setTitle("Rules")
                                .setDescription("Now please send me the rules."))
                                .build()).queue();
                        new EventWaiter().newOnMessageEventWaiter(msgevent2 -> {
                            msgevent2.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                    .setTitle("Role to assign"))
                                    .setDescription("Now please send me the name of the role which the user will get after he accepted the rules.")
                                    .build()).queue();
                            new EventWaiter().newOnMessageEventWaiter(msgevent3 -> {
                                try {
                                    msgevent3.getGuild().getRolesByName(msgevent3.getMessage().getContentRaw(), false).get(0);
                                    msgevent3.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder().setTitle("Successfully set the rules")).build()).queue();
                                    Message rules = msgevent.getMessage().getMentionedChannels().get(0).sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                            .setTitle("Rules"))
                                            .setDescription(msgevent2.getMessage().getContentDisplay())
                                            .build()).complete();
                                    rules.addReaction("✅").queue();
                                    rules.addReaction("❌").queue();

                                    event.getRethink().updateRules(event.getGuild().getId(), rules.getId(), msgevent3.getGuild().getRolesByName(msgevent3.getMessage().getContentRaw(), false).get(0).getId());
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
