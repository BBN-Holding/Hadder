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
                case "de":
                    setLanguage("de", "German", event);
                    break;
                case "en":
                    setLanguage("en", "English", event);
                    break;
                case "es":
                    setLanguage("es", "Spanish", event);
                    break;
                case "fr":
                    setLanguage("fr", "French", event);
                    break;
                case "ru":
                    setLanguage("ru", "Russian", event);
                    break;
                case "tr":
                    setLanguage("tr", "Turkish", event);
                    break;
                case "zh":
                    setLanguage("zh", "Chinese", event);
                    break;
            }
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    public void setLanguage(String language_code, String language , CommandEvent event) {
        event.getRethink().setLanguage(event.getAuthor().getId(), language_code);
        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Language set")
                .setDescription(language + " is your new language now.")
                .build()).queue();
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
