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
                                        .setTitle("Successfully activated")
                                        .setDescription("I successfully activated the invite link detection for this guild.")
                                        .build()).queue();
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                        .setTitle("Already activated")
                                        .setDescription("The invite link detection is already activated on this guild.")
                                        .build()).queue();
                            }
                            break;

                        case "off":
                            if (event.getRethink().getInviteDetection(event.getGuild().getId())) {
                                event.getRethink().setInviteDetection(event.getGuild().getId(), false);
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                                        .setTitle("Successfully deactivated")
                                        .setDescription("I successfully deactivated the invite link detection for this guild.")
                                        .build()).queue();
                            } else {
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                                        .setTitle("Already deactivated")
                                        .setDescription("The invite link detection is already deactivated on this guild.")
                                        .build()).queue();
                            }
                            break;
                    }
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
            }
        } else {
            event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"invitedetect", "detectinvite"};
    }

    @Override
    public String description() {
        return "Activate or deactivate the Discord invite link detection.";
    }

    @Override
    public String usage() {
        return "<on/off>";
    }
}
