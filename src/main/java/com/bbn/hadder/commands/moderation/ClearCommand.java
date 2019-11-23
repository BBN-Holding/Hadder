package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;


public class ClearCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.MESSAGE_MANAGE)  || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                if (event.getGuild().getMemberById(event.getJDA().getSelfUser().getId()).hasPermission(Permission.MESSAGE_MANAGE)) {
                    try {
                        int nbToDelete = Integer.parseInt(args[0]);
                        if(nbToDelete < 1 || nbToDelete > 200) {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You have to choose a number between 1 and 200!").build()).queue();
                            return;
                        }
                        List<Message> history = event.getTextChannel().getHistory().retrievePast(nbToDelete +1).complete();
                        List<Message> msgToDelete = new ArrayList<>();
                        msgToDelete.addAll(history);
                        event.getTextChannel().deleteMessages(msgToDelete).queue();
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setDescription("Successfully deleted " + nbToDelete + " messages.").build()).queue();
                    } catch (NumberFormatException e) {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You have to specify a number!").build()).queue();
                        event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
                    }
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION, builder).build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder).setDescription("You have to specify a number!").build()).queue();
            event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"clear"};
    }

    @Override
    public String description() {
        return "Deletes the specified number of messages";
    }

    @Override
    public String usage() {
        return "<Number>";
    }
}
