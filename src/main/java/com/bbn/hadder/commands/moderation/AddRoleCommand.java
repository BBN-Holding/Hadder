package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class AddRoleCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            if (event.getMessage().getMentionedMembers().size() > 0 && event.getMessage().getMentionedRoles().size() == 1) {
                for (Member member : event.getMessage().getMentionedMembers()) {
                    if (event.getGuild().getSelfMember().canInteract(member)) {
                        if (!member.getRoles().contains(event.getMessage().getMentionedRoles().get(0))) {
                            event.getGuild().addRoleToMember(member, event.getMessage().getMentionedRoles().get(0))
                                    .reason("Role Command executed by " + event.getAuthor().getAsTag()).queue();
                        }
                    }
                }
                event.getChannel().sendMessage(
                        new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO,
                                new EmbedBuilder().setTitle("Successfully added all " +
                                        event.getMessage().getMentionedMembers().size() +
                                        " Members the " + event.getMessage().getMentionedRoles().get(0).getName() + " Role")).build()).queue();
            } else if (event.getMessage().getMentionedRoles().size() > 1) {
                for (Role role : event.getMessage().getMentionedRoles()) {
                    if (role == event.getMessage().getMentionedRoles().get(event.getMessage().getMentionedRoles().size() - 1)) {
                        if (event.getGuild().getSelfMember().canInteract(role)) {
                            int highestrole = 0;
                            for (Role testrole : event.getGuild().getSelfMember().getRoles()) {
                                if (testrole.getPosition()>highestrole) {
                                    highestrole=testrole.getPosition();
                                }
                            }
                            for (Member member : event.getGuild().getMembers()) {
                                if (member.getRoles().contains(role)) {
                                    event.getGuild().addRoleToMember(member, event.getMessage().getMentionedRoles().get(0))
                                            .reason("Role Command executed by " + event.getAuthor().getAsTag()).queue();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"addrole", "addroles"};
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String usage() {
        return null;
    }
}
