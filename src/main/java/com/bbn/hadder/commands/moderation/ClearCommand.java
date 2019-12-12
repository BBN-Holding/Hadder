package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ClearCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.MESSAGE_MANAGE)  || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                if (event.getGuild().getMemberById(event.getJDA().getSelfUser().getId()).hasPermission(Permission.MESSAGE_MANAGE)) {
                    try {
                        int nbToDelete = Integer.parseInt(args[0]);
                        if(nbToDelete < 1 || nbToDelete > 99) {
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.clear.number.error.description")).build()).queue();
                            return;
                        }
                        List<Message> history = event.getTextChannel().getHistory().retrievePast(nbToDelete +1).complete();
                        List<Message> msgToDelete = new ArrayList<>();
                        msgToDelete.addAll(history);
                        event.getTextChannel().deleteMessages(msgToDelete).queue();
                        Message msg = event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.clear.success.description", String.valueOf(nbToDelete))).build()).complete();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        msg.delete().queue();
                    } catch (NumberFormatException e) {
                        event.getHelpCommand().sendHelp(this, event);
                    }
                } else {
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
            }
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"clear"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.moderation.clear.help.description");
    }

    @Override
    public String usage() {
        return MessageEditor.handle("en", "number");
    }
}
