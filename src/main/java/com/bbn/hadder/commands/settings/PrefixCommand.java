package com.bbn.hadder.commands.settings;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class PrefixCommand implements Command {
    public void executed(String[] args, MessageReceivedEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.MANAGE_SERVER)) {
            if (args.length == 1) {
                if (!args[0].contains("\"")) {

                    Rethink.update("server", event.getGuild().getId(), "prefix", args[0]);

                    EmbedBuilder builder = new EmbedBuilder();

                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("✅ Successfully set ✅").setDescription("I successfully set the new prefix for this server to " + args[0]).build()).queue();
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("The prefix must not contain **\"**").build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to set a prefix.").build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }
}
