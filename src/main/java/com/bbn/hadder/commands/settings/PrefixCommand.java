package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;


public class PrefixCommand implements Command {

    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            event.getRethink().setUserPrefix(args[0], event.getAuthor().getId());
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setTitle("✅ Successfully set ✅").setDescription("I successfully set the new prefix for you to " + args[0]).build()).queue();
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You have to set a prefix.").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }

    @Override
    public String description() {
        return "Sets a new Prefix";
    }

    @Override
    public String usage() {
        return "<New Prefix>";
    }
}
