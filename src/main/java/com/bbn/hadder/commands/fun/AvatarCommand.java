package com.bbn.hadder.commands.fun;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

/**
 * @author Skidder / GregTCLTK
 */

public class AvatarCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 0) {
            Member member = e.getMember();
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.fun.avatar.success.title",
                            member.getUser().getAsTag(),
                            "",
                            "")
                    .setImage(member.getUser().getAvatarUrl())
                    .setFooter(member.getUser().getAsTag())
                    .build()).queue();
        } else if (e.getMessage().getMentionedMembers().size() == 1) {
            Member member = e.getMessage().getMentionedMembers().get(0);
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.fun.avatar.success.title",
                            member.getUser().getAsTag(),
                            "",
                            "")
                    .setImage(member.getUser().getAvatarUrl())
                    .setFooter(member.getUser().getAsTag())
                    .build()).queue();
        } else if (args[0].length() == 18) {
            try {
                User u = e.getGuild().getMemberById(args[0]).getUser();
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.fun.avatar.success.title",
                                u.getAsTag(),
                                "",
                                "")
                                .setImage(u.getAvatarUrl())
                                .setFooter(u.getAsTag())
                                .build()).queue();
            } catch (NullPointerException ignore) {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.fun.avatar.error.title",
                        "commands.fun.avatar.error.description"
                ).build()).queue();
            }

        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"avatar"};
    }

    @Override
    public String description() {
        return "commands.fun.avatar.help.description";
    }

    @Override
    public String usage() {
        return "[User]";
    }

    @Override
    public String example() {
        return "@Skidder";
    }
}
