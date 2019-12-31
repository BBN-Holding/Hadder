package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;

@Perms(Perm.MANAGE_SERVER)
public class InviteDetectCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            String opinion = args[0].toLowerCase();
            switch (opinion) {
                case "on":
                    if (!event.getRethink().getInviteDetection(event.getGuild().getId())) {
                        event.getRethink().setInviteDetection(event.getGuild().getId(), true);
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.invitedetect.activate.success.title",
                                        "commands.moderation.invitedetect.activate.success.description")
                                        .build()).queue();
                    } else {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "commands.moderation.invitedetect.activate.error.title",
                                "commands.moderation.invitedetect.activate.error.description")
                                .build()).queue();
                    }
                    break;

                case "off":
                    if (event.getRethink().getInviteDetection(event.getGuild().getId())) {
                        event.getRethink().setInviteDetection(event.getGuild().getId(), false);
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.invitedetect.deactivate.success.title",
                                "commands.moderation.invitedetect.deactivate.success.description")
                                .build()).queue();
                    } else {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "commands.moderation.invitedetect.deactivate.error.title",
                                "commands.moderation.invitedetect.deactivate.error.description")
                                .build()).queue();
                    }
                    break;
                default:
                    event.getHelpCommand().sendHelp(this, event);
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
        return "commands.moderation.invitedetect.help.description";
    }

    @Override
    public String usage() {
        return "[on/off]";
    }

    @Override
    public String example() {
        return "on";
    }
}
