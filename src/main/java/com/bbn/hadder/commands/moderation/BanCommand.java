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

public class BanCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.BAN_MEMBERS) || event.getGuild().getOwner().getId().equals(event.getAuthor().getId())) {
                if (event.getMessage().getMentionedMembers().size() == 1) {
                    Member victim = event.getMessage().getMentionedMembers().get(0);
                    if (!event.getAuthor().getId().equals(victim.getId())) {
                        if (!event.getJDA().getSelfUser().getId().equals(victim.getId())) {
                            if (event.getGuild().getSelfMember().canInteract(victim)) {
                                event.getGuild().ban(victim, 0, "Banned by " + event.getAuthor().getAsTag()).queue();
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setTitle("✅ Successfully banned ✅").setDescription("I successfully baned " + victim.getUser().getName() + ".").build()).queue();
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("I can not ban myself").build()).queue();
                        }
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setTitle("Not possible").setDescription("You can't ban yourself.").build()).queue();
                    }
                } else if (event.getMessage().getMentionedMembers().size() == 0) {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You have to mention at least one user!").build()).queue();
                    event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
                } else if (event.getMessage().getMentionedMembers().size() > 1) {
                    for (int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                        Member member = event.getMessage().getMentionedMembers().get(i);
                        if (!event.getAuthor().getId().equals(member.getId())) {
                            if (!event.getJDA().getSelfUser().getId().equals(member.getId())) {
                                if (event.getGuild().getSelfMember().canInteract(member)) {
                                    event.getGuild().ban(member, 0).reason("Mass Ban by " + event.getAuthor().getAsTag()).queue();
                                } else {
                                    EmbedBuilder builder = new EmbedBuilder();
                                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                }
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("I can not ban myself!").build()).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You can't ban yourself.").build()).queue();
                        }
                    }
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setTitle("✅ Successfully banned ✅").setDescription("I successfully banned " + event.getMessage().getMentionedMembers().size() + " Members!").build()).queue();
                }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"ban"};
    }

    @Override
    public String description() {
        return "Bans one ore more user from the server";
    }

    @Override
    public String usage() {
        return "<@User>";
    }
}
