package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class BanCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.BAN_MEMBERS) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                if (event.getMessage().getMentionedMembers().size() == 1) {
                    Member victim = event.getMessage().getMentionedMembers().get(0);
                    if (!event.getAuthor().getId().equals(victim.getId())) {
                        if (!event.getJDA().getSelfUser().getId().equals(victim.getId())) {
                            if (event.getGuild().getSelfMember().canInteract(victim)) {
                                event.getGuild().ban(victim, 0, "Banned by " + event.getAuthor().getAsTag()).queue();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.success.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.success.description", victim.getUser().getName() + ".")).build()).queue();
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                            }
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING).setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.error.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.myself.error.description")).build()).queue();
                        }
                    } else {
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING).setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.error.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.yourself.error.description")).build()).queue();
                    }
                } else if (event.getMessage().getMentionedMembers().size() == 0) {
                    event.getHelpCommand().sendHelp(this, event);
                } else if (event.getMessage().getMentionedMembers().size() > 1) {
                    for (int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                        Member member = event.getMessage().getMentionedMembers().get(i);
                        if (!event.getAuthor().getId().equals(member.getId())) {
                            if (!event.getJDA().getSelfUser().getId().equals(member.getId())) {
                                if (event.getGuild().getSelfMember().canInteract(member)) {
                                    event.getGuild().ban(member, 0).reason("Mass Ban by " + event.getAuthor().getAsTag()).queue();
                                } else {
                                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                }
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING).setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.error.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.myself.error.description")).build()).queue();
                            }
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING).setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.error.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.yourself.error.description")).build()).queue();
                        }
                    }
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.success.title")).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.ban.success.description", String.valueOf(event.getMessage().getMentionedMembers().size()))).build()).queue();
                }
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"ban"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.moderation.ban.help.description");
    }

    @Override
    public String usage() {
        return "<@User>";
    }
}
