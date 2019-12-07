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
                .setTitle("Hadder - About")
                .setDescription("Hadder is an open source Discord bot.")
                .addField("Support the Developers", "Hadder is completely free for everyone. We would appreciate it you donate some money [here](https://donatebot.io/checkout/448554629282922527?buyer=" + event.getAuthor().getId() + "). :smiley:", true)
                .setThumbnail("https://bigbotnetwork.com/images/Hadder.png")
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"about", "info"};
    }

    @Override
    public String description() {
        return "Shows infos about Hadder.";
    }

    @Override
    public String usage() {
        return "";
    }
}
