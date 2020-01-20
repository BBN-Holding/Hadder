package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;

@Perms(Perm.MANAGE_SERVER)
public class PrefixCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            if (!args[0].contains("\"")) {
                e.getRethink().setGuildPrefix(args[0], e.getGuild().getId());
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.moderation.prefix.success.title",
                        "✅",
                        "commands.moderation.prefix.success.description",
                        args[0]).build()
                ).queue();
            } else {
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.WARNING,
                                "",
                                "commands.moderation.prefix.error.description").build()).queue();
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }

    @Override
    public String description() {
        return "commands.moderation.prefix.help.description";
    }

    @Override
    public String usage() {
        return "[New Prefix]";
    }

    @Override
    public String example() {
        return "!";
    }
}
