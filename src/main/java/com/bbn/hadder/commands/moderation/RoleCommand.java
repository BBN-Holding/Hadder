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
                if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                    switch (args[0].toLowerCase()) {
                        case "add":
                            if (event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedRoles().size() > 0) {
                                for (Member member : event.getMessage().getMentionedMembers()) {
                                    for (Role role : event.getMessage().getMentionedRoles()) {
                                        if (event.getGuild().getSelfMember().canInteract(member)) {
                                            if (event.getGuild().getSelfMember().canInteract(role)) {
                                                event.getGuild().addRoleToMember(member, role).reason("Role added by " + event.getAuthor().getAsTag()).queue();
                                            } else {
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                            }
                                        } else {
                                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                        }
                                    }
                                }
                                event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle("✅ Successfully added role(s) ✅")
                                        .setDescription("I successfully added " + event.getMessage().getMentionedRoles().size() + " roles to " + event.getMessage().getMentionedMembers().size() + " members.")
                                        .build()).queue();
                            }
                            break;

                        case "remove":
                            if (event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedRoles().size() > 0) {
                                for (Member member : event.getMessage().getMentionedMembers()) {
                                    for (Role role : event.getMessage().getMentionedRoles()) {
                                        if (event.getGuild().getSelfMember().canInteract(member)) {
                                            if (event.getGuild().getSelfMember().canInteract(role)) {
                                                event.getGuild().removeRoleFromMember(member, role).reason("Role removed by " + event.getAuthor().getAsTag()).queue();
                                            } else {
                                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                            }
                                        } else {
                                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                        }
                                    }
                                }
                                event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle("✅ Successfully removed role(s) ✅")
                                        .setDescription("I successfully removed " + event.getMessage().getMentionedRoles().size() + " roles from " + event.getMessage().getMentionedMembers().size() + " members.")
                                        .build()).queue();
                            }
                            break;
                    }
                } else {
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
            }
        } else {
            event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
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
        return "add/remove <@role> <@user>";
    }
}
