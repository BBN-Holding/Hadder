package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;


public class RebootCommand implements Command {


    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            System.exit(69);
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"restart"};
    }

    @Override
    public String description() {
        return "Restart the bot";
    }

    @Override
    public String usage() {
        return "";
    }
}
