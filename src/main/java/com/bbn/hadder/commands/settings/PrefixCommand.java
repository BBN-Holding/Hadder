package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;


public class PrefixCommand implements Command {

    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            event.getRethink().setUserPrefix(args[0], event.getAuthor().getId());
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                    .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.settings.prefix.success.title"))
                    .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.settings.prefix.success.description", args[0]))
                    .build()).queue();
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.settings.prefix.help.description");
    }

    @Override
    public String usage() {
        return MessageEditor.handle("en", "prefix");
    }
}
