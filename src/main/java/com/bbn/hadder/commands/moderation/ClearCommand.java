package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ClearCommand implements Command {

    @Override
    public void executed(String[] args, MessageReceivedEvent event) {
        if (args.length > 0) {
            if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.MESSAGE_MANAGE) || event.getGuild().getOwnerId().equals(event.getAuthor().getId())) {
                if (event.getGuild().getMemberById(event.getJDA().getSelfUser().getId()).hasPermission(Permission.MESSAGE_MANAGE)) {
                    try {
                        int nbToDelete = Integer.parseInt(args[0]);
                        if(nbToDelete < 1 || nbToDelete > 200) {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to choose a number between 1 and 200!").build()).queue();
                            return;
                        }
                        List<Message> history = event.getTextChannel().getHistory().retrievePast(nbToDelete +1).complete();
                        List<Message> msgToDelete = new ArrayList<>();
                        msgToDelete.addAll(history);
                        event.getTextChannel().deleteMessages(msgToDelete).queue();
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setDescription("Successfully deleted " + nbToDelete + " messages.").build()).queue();
                    } catch (NumberFormatException e) {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to specify a number!").build()).queue();
                    }
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_SELF_PERMISSION, builder).build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_PERMISSION, builder).build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to specify a number!").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"clear"};
    }

    @Override
    public String description() {
        return "Clears messages";
    }

    @Override
    public String usage() {
        return labels()[0]+" <Number>";
    }
}
