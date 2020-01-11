package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

@Perms(Perm.MANAGE_ROLES)
public class RoleCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
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
                                            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                        }
                                    } else {
                                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                    }
                                }
                            }
                            event.getChannel().sendMessage(
                                    event.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.role.add.success.title",
                                            "",
                                            "",
                                            "commands.moderation.role.add.success.description",
                                            String.valueOf(event.getMessage().getMentionedRoles().size()),
                                            String.valueOf(event.getMessage().getMentionedMembers().size()))
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
                                            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                        }
                                    } else {
                                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                    }
                                }
                            }
                            event.getChannel().sendMessage(
                                    event.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.role.remove.success.title",
                                            "",
                                            "",
                                            "commands.moderation.role.remove.success.description",
                                            String.valueOf(event.getMessage().getMentionedRoles().size()),
                                            String.valueOf(event.getMessage().getMentionedMembers().size()))
                                            .build()).queue();
                        }
                        break;
                    default:
                        event.getHelpCommand().sendHelp(this, event);
                        break;
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"role", "roles"};
    }

    @Override
    public String description() {
        return "commands.moderation.role.help.description";
    }

    @Override
    public String usage() {
        return "[add/remove] [Role>] [User]";
    }

    @Override
    public String example() {
        return "add @Skidder @Epic-Gamer";
    }
}
