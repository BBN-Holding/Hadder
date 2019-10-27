package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BanCommand implements Command {

    public void executed(String[] args, MessageReceivedEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.BAN_MEMBERS) || event.getGuild().getOwner().getId().equals(event.getAuthor().getId())) {
                if (event.getMessage().getMentionedMembers().size() == 1) {
                    Member victim = event.getMessage().getMentionedMembers().get(0);
                    if (!event.getAuthor().getId().equals(victim.getId())) {
                        if (event.getGuild().getSelfMember().canInteract(victim)) {
                            event.getGuild().ban(victim, 0, "Banned by " + event.getAuthor().getAsTag()).queue();
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("✅ Successfully banned ✅").setDescription("I successfully baned " + victim.getUser().getName() + ".").build()).queue();
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_SELF_PERMISSION, builder).build()).queue();
                        }
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setTitle("Not possible").setDescription("You can't ban yourself.").build()).queue();
                    }
                } else if (event.getMessage().getMentionedMembers().size() == 0) {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to mention a user!").build()).queue();
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("We will be adding multiple banning within a command in the future.").build()).queue();
                }

        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_PERMISSION, builder).build()).queue();
        }
    }
}
