package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;

public class InviteDetectCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                String opinion = args[0].toLowerCase();
                    switch (opinion) {
                        case "on":
                            if (!event.getRethink().getInviteDetection(event.getGuild().getId())) {
                                event.getRethink().setInviteDetection(event.getGuild().getId(), true);
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.activate.success.title"))
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.activate.success.description"))
                                        .build()).queue();
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.activate.error.title"))
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.activate.error.description"))
                                        .build()).queue();
                            }
                            break;

                        case "off":
                            if (event.getRethink().getInviteDetection(event.getGuild().getId())) {
                                event.getRethink().setInviteDetection(event.getGuild().getId(), false);
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.deactivate.success.title"))
                                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.deactivate.success.description"))
                                        .build()).queue();
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.deactivate.error.title"))
                                        .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.moderation.invitedetect.deactivate.error.description"))
                                        .build()).queue();
                            }
                            break;
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
        return new String[]{"invitedetect", "detectinvite"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.moderation.invitedetect.help.description");
    }

    @Override
    public String usage() {
        return "<on/off>";
    }
}
