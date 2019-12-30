package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Perms(Perm.MANAGE_MESSAGES)
public class ClearCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                if (args[0].equals("all")) {
                    List<Message> msg = event.getTextChannel().getIterableHistory().complete();
                    for (Message message : msg) {
                        message.delete().queue();
                    }
                    Message message = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.moderation.lear.all.success.title",
                            "",
                            "commands.moderation.lear.all.success.description",
                            String.valueOf(msg.size()))
                            .build()).complete();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    message.delete().queue();
                } else {
                    try {
                        int nbToDelete = Integer.parseInt(args[0]);
                        if (nbToDelete < 1 || nbToDelete > 99) {
                            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.WARNING, "", "commands.moderation.clear.number.error.description").build()).queue();
                        } else {
                            List<Message> history = event.getTextChannel().getHistory().retrievePast(nbToDelete + 1).complete();
                            List<Message> msgToDelete = new ArrayList<>(history);
                            event.getTextChannel().deleteMessages(msgToDelete).queue();
                            if (nbToDelete == 1) {
                                Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.moderation.clear.success.title",
                                        "commands.moderation.clear.success.description.singular").build()).complete();
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                msg.delete().queue();
                            } else {
                                Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.moderation.clear.success.title", "",
                                        "commands.moderation.clear.success.description.plural", String.valueOf(nbToDelete)).build()).complete();
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                msg.delete().queue();
                            }
                        }
                    } catch (NumberFormatException e) {
                        event.getHelpCommand().sendHelp(this, event);
                    } catch (IllegalArgumentException e) {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.WARNING, "commands.moderation.clear.message.error.title", "commands.moderation.clear.message.error.description")
                                .build()).queue();
                    }
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
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
        return "commands.moderation.clear.help.description";
    }

    @Override
    public String usage() {
        return "[Amount]";
    }

    @Override
    public String example() {
        return "69";
    }
}
