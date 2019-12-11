package com.bbn.hadder.commands.general;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class AboutCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.general.about.success.title"))
                .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.general.about.success.description"))
                .addField(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.general.about.success.field.one.title"), MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.general.about.success.field.one.description") + "(https://donatebot.io/checkout/448554629282922527?buyer=" + event.getAuthor().getId() + "). :smiley:", true)
                .setThumbnail("https://bigbotnetwork.com/images/Hadder.png")
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"about", "info"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.general.about.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
