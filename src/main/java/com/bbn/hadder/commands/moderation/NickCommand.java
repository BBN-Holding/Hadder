package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@Perms(Perm.MANAGE_NICKNAMES)
public class NickCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getSelfMember().hasPermission(Permission.NICKNAME_MANAGE)) {
            if (event.getMessage().getMentionedMembers().size() == 1) {
                if (!event.getMessage().getMentionedMembers().get(0).getId().equals(event.getGuild().getSelfMember().getId())) {
                    if (event.getGuild().getSelfMember().canInteract(event.getMessage().getMentionedMembers().get(0))) {
                        if (args.length > 1) {
                            if (event.getMessage().getContentRaw().startsWith(event.getRethinkUser().getPrefix())) {
                                event.getGuild().modifyNickname(event.getMessage().getMentionedMembers().get(0), event.getMessage().getContentRaw().replaceFirst(event.getRethinkUser().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                                event.getTextChannel().sendMessage(
                                        event.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.INFO,
                                                "commands.moderation.nick.success.title",
                                                "✅",
                                                "commands.moderation.nick.success.description",
                                                event.getMessage().getMentionedMembers().get(0).getUser().getAsTag()
                                        ).build()).queue();
                            } else if (event.getMessage().getContentRaw().startsWith(event.getRethinkServer().getPrefix())) {
                                event.getGuild().modifyNickname(event.getMessage().getMentionedMembers().get(0), event.getMessage().getContentRaw().replaceFirst(event.getRethinkServer().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                                event.getTextChannel().sendMessage(
                                        event.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.INFO,
                                                "commands.moderation.nick.success.title",
                                                "✅",
                                                "commands.moderation.nick.success.description", event.getMessage().getMentionedMembers().get(0).getUser().getAsTag()
                                        ).build()).queue();
                            }
                        } else {
                            event.getHelpCommand().sendHelp(this, event);
                        }
                    } else {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                    }
                } else {
                    if (event.getMessage().getContentRaw().startsWith(event.getRethinkUser().getPrefix())) {
                        event.getGuild().getSelfMember().modifyNickname(event.getMessage().getContentRaw().replaceFirst(event.getRethinkUser().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.nick.success.title",
                                        "✅",
                                        "commands.moderation.nick.myself.success.description",
                                        "").build()).queue();
                    } else if (event.getMessage().getContentRaw().startsWith(event.getRethinkServer().getPrefix())) {
                        event.getGuild().getSelfMember().modifyNickname(event.getMessage().getContentRaw().replaceFirst(event.getRethinkServer().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.nick.success.title",
                                        "✅",
                                        "commands.moderation.nick.myself.success.description", ""
                                ).build()).queue();
                    }
                }
            } else if (event.getMessage().getMentionedMembers().size() == 0) {
                event.getHelpCommand().sendHelp(this, event);
            } else {
                for (int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                    Member member = event.getMessage().getMentionedMembers().get(i);
                    if (!event.getJDA().getSelfUser().getId().equals(member.getId())) {
                        event.getGuild().modifyNickname(member, args[args.length - 1]).reason("Mass Nicked by " + event.getAuthor().getAsTag()).queue();
                    } else {
                        event.getGuild().getSelfMember().modifyNickname(args[args.length - 1]).reason("Mass Nicked by " + event.getAuthor().getAsTag()).queue();
                    }
                }
                event.getTextChannel().sendMessage(
                        event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.nick.success.title",
                                "✅",
                                "commands.moderation.nick.myself.success.description", String.valueOf(event.getMessage().getMentionedMembers().size())
                        ).build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"nick"};
    }

    @Override
    public String description() {
        return "commands.moderation.nick.help.description";
    }

    @Override
    public String usage() {
        return "[User(s)] [New nickname]";
    }

    @Override
    public String example() {
        return "@Skidder Cutie";
    }
}
