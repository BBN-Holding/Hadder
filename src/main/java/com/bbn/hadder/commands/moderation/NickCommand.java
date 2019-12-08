package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class NickCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().hasPermission(Permission.NICKNAME_MANAGE) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.NICKNAME_MANAGE)) {
                if (event.getMessage().getMentionedMembers().size() == 1) {
                    if (!event.getMessage().getMentionedMembers().get(0).getId().equals(event.getGuild().getSelfMember().getId())) {
                        if (event.getGuild().getSelfMember().canInteract(event.getMessage().getMentionedMembers().get(0))) {
                            if (args.length > 1) {
                                if (event.getMessage().getContentRaw().startsWith(event.getRethink().getUserPrefix(event.getMember().getId()))) {
                                    event.getGuild().modifyNickname(event.getMessage().getMentionedMembers().get(0), event.getMessage().getContentRaw().replaceFirst(event.getRethink().getUserPrefix(event.getMember().getId()) + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("✅ Successfully nicked ✅").setDescription("I successfully nicked " + event.getMessage().getMentionedMembers().get(0).getUser().getAsTag() + ".").build()).queue();
                                } else if (event.getMessage().getContentRaw().startsWith(event.getRethink().getGuildPrefix(event.getGuild().getId()))) {
                                    event.getGuild().modifyNickname(event.getMessage().getMentionedMembers().get(0), event.getMessage().getContentRaw().replaceFirst(event.getRethink().getGuildPrefix(event.getGuild().getId()) + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("✅ Successfully nicked ✅").setDescription("I successfully nicked " + event.getMessage().getMentionedMembers().get(0).getUser().getAsTag() + ".").build()).queue();
                                }
                            } else {
                                event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
                            }
                        } else {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                        }
                    } else {
                        if (event.getMessage().getContentRaw().startsWith(event.getRethink().getUserPrefix(event.getMember().getId()))) {
                            event.getGuild().getSelfMember().modifyNickname(event.getMessage().getContentRaw().replaceFirst(event.getRethink().getUserPrefix(event.getMember().getId()) + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("✅ Successfully nicked ✅").setDescription("I successfully changed my nickname.").build()).queue();
                        } else if (event.getMessage().getContentRaw().startsWith(event.getRethink().getGuildPrefix(event.getGuild().getId()))) {
                            event.getGuild().getSelfMember().modifyNickname(event.getMessage().getContentRaw().replaceFirst(event.getRethink().getGuildPrefix(event.getGuild().getId()) + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("✅ Successfully nicked ✅").setDescription("I successfully changed my nickname.").build()).queue();
                        }
                    }
                } else if (event.getMessage().getMentionedMembers().size() == 0) {
                    event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
                } else {
                    for (int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                        Member member = event.getMessage().getMentionedMembers().get(i);
                            if (!event.getJDA().getSelfUser().getId().equals(member.getId())) {
                                event.getGuild().modifyNickname(member, args[args.length - 1]).reason("Mass Nicked by " + event.getAuthor().getAsTag()).queue();
                            } else {
                                event.getGuild().getSelfMember().modifyNickname(args[args.length - 1]).reason("Mass Nicked by " + event.getAuthor().getAsTag()).queue();
                            }
                    }
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("✅ Successfully nicked ✅").setDescription("I successfully nicked " + event.getMessage().getMentionedMembers().size() + " Members!").build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"nick"};
    }

    @Override
    public String description() {
        return "Rename a one or more user.";
    }

    @Override
    public String usage() {
        return "<@user> <New Nickname>";
    }
}
