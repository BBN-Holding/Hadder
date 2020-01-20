package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class LanguageCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
            case "de":
                setLanguage("de", "German", e);
                break;
            case "en":
                setLanguage("en", "English", e);
                break;
            case "es":
                setLanguage("es", "Spanish", e);
                break;
            case "fr":
                setLanguage("fr", "French", e);
                break;
            case "ru":
                setLanguage("ru", "Russian", e);
                break;
            case "tr":
                setLanguage("tr", "Turkish", e);
                break;
            case "zh":
                setLanguage("zh", "Chinese", e);
                break;
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    public void setLanguage(String language_code, String language, CommandEvent e) {
        e.getRethink().setLanguage(e.getAuthor().getId(), language_code);
        e.getTextChannel()
                .sendMessage(
                        e.getMessageEditor()
                                .getMessage(MessageEditor.MessageType.INFO, "commands.settings.language.success.title",
                                        "", "commands.settings.language.success.description", language)
                                .build())
                .queue();
    }

    @Override
    public String[] labels() {
        return new String[] { "language" };
    }

    @Override
    public String description() {
        return "commands.settings.language.help.description";
    }

    @Override
    public String usage() {
        return "[Language code]";
    }

    @Override
    public String example() {
        return "de";
    }
}
