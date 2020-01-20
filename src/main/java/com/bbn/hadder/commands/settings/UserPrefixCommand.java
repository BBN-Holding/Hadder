package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class UserPrefixCommand implements Command {

    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            e.getRethink().setUserPrefix(args[0], e.getAuthor().getId());
            e.getTextChannel()
                    .sendMessage(e.getMessageEditor()
                            .getMessage(MessageEditor.MessageType.INFO, "commands.settings.prefix.success.title", "",
                                    "commands.settings.prefix.success.description", args[0])
                            .build())
                    .queue();
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[] { "userprefix" };
    }

    @Override
    public String description() {
        return "commands.settings.prefix.help.description";
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
