package com.bbn.hadder.commands.nsfw;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import com.bbn.hadder.utils.Request;

public class EroticCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getTextChannel().isNSFW()) {

            String url = Request.get("https://nekos.life/api/v2/img/erok");

            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                    .setAuthor(e.getMessageEditor().getTerm("commands.nsfw.gif.error.title"), url)
                    .setImage(url)
                    .setFooter("Erotic")
                    .build()).queue();

        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_NSFW).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"erotic"};
    }

    @Override
    public String description() {
        return "commands.nsfw.erotic.help.description";
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
