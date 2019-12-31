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
        event.getTextChannel().sendMessage(
                event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.general.about.success.title",
                        "commands.general.about.success.description")
                        .addField(event.getMessageEditor().getTerm("commands.general.about.success.field.one.title"), event.getMessageEditor().getTerm("commands.general.about.success.field.one.description", "(https://donatebot.io/checkout/448554629282922527?buyer=" + event.getAuthor().getId() + "). :smiley:", ""), true)
                .setThumbnail("https://bigbotnetwork.com/images/Hadder.png")
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"about"};
    }

    @Override
    public String description() {
        return "commands.general.about.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
