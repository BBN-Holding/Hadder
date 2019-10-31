package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class PrefixCommand implements Command {
    public void executed(String[] args, MessageReceivedEvent event) {
        if (args.length == 1) {
            if (!args[0].contains("\"")) {

                Rethink.update("user", event.getAuthor().getId(), "prefix", args[0]);

                EmbedBuilder builder = new EmbedBuilder();

                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("✅ Successfully set ✅").setDescription("I successfully set the new prefix for you to " + args[0]).build()).queue();
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("The prefix must not contain **\"**").build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to set a prefix.").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }

    @Override
    public String description() {
        return "Changes the prefix";
    }

    @Override
    public String usage() {
        return labels()[0]+" <New Prefix>";
    }
}
