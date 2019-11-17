package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class RoleCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
                    switch (args[0].toLowerCase()) {
                        case "add":
                            if (event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedRoles().size() > 0) {
                                for (Member member : event.getMessage().getMentionedMembers()) {
                                    for (Role role : event.getMessage().getMentionedRoles()) {
                                        if (event.getGuild().getSelfMember().canInteract(member)) {
                                            if (event.getGuild().getSelfMember().canInteract(role)) {
                                                event.getGuild().addRoleToMember(member, role).reason("Role added by " + event.getAuthor().getAsTag()).queue();
                                            } else {
                                                EmbedBuilder builder = new EmbedBuilder();
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                            }
                                        } else {
                                            EmbedBuilder builder = new EmbedBuilder();
                                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                                        }
                                    }
                                }
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder)
                                        .setTitle("✅ Successfully added role(s) ✅")
                                        .setDescription("I successfully added " + event.getMessage().getMentionedRoles().size() + " roles to " + event.getMessage().getMentionedMembers().size() + " members.")
                                        .build()).queue();
                            }
                            break;

                        case "remove":
                            event.getTextChannel().sendMessage("SOON").queue();
                            break;
                    }
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage("Missing args").queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"role", "roles"};
    }

    @Override
    public String description() {
        return "Adds and removes roles from one or more user";
    }

    @Override
    public String usage() {
        return "<@role> <@user>";
    }
}
