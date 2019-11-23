package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;


public class KickCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.KICK_MEMBERS) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (event.getMessage().getMentionedMembers().size() == 1) {
                Member victim = event.getMessage().getMentionedMembers().get(0);
                if (!event.getAuthor().getId().equals(victim.getId())) {
                    if (!event.getJDA().getSelfUser().getId().equals(victim.getId())) {
                        if (event.getGuild().getSelfMember().canInteract(victim)) {
                            event.getGuild().kick(victim, "Kicked by " + event.getAuthor().getAsTag()).queue();
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setTitle("✅ Successfully kicked ✅").setDescription("I successfully kicked " + victim.getUser().getName() + ".").build()).queue();
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                        }
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("I can not kick myself!").build()).queue();
                    }
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You can't kick yourself.").build()).queue();
                }
            } else if (event.getMessage().getMentionedMembers().size() == 0) {
                event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
            } else if (event.getMessage().getMentionedMembers().size() > 1) {
                for (int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                    Member member = event.getMessage().getMentionedMembers().get(i);
                    if (!event.getAuthor().getId().equals(member.getId())) {
                        if (!event.getJDA().getSelfUser().getId().equals(member.getId())) {
                            if (event.getGuild().getSelfMember().canInteract(member)) {
                                event.getGuild().kick(member).reason("Mass Kicked by " + event.getAuthor().getAsTag()).queue();
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("I can not kick myself!").build()).queue();
                        }
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You can't kick yourself.").build()).queue();
                    }
                }
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setTitle("✅ Successfully kicked ✅").setDescription("I successfully kicked " + event.getMessage().getMentionedMembers().size() + " Members!").build()).queue();
            }
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"kick"};
    }

    @Override
    public String description() {
        return "Kicks one or more user from the server";
    }

    @Override
    public String usage() {
        return "<@User>";
    }
}
