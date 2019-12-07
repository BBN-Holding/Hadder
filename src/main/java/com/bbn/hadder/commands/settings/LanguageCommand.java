package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class LanguageCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "DE":
                    event.getRethink().setLanguage(event.getAuthor().getId(), "DE");
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle("Language set")
                            .setDescription("German is your new language now.")
                            .build()).queue();
                    break;
            }
        } else {
            event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"language"};
    }

    @Override
    public String description() {
        return "Sets the new primary language for a user.";
    }

    @Override
    public String usage() {
        return "<Language code>";
    }
}
