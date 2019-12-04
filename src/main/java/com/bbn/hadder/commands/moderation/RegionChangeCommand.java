package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;

public class RegionChangeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_SERVER)) {
                if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                    // CHANGE REGION
                } else event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
            } else event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        } else event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
    }

    @Override
    public String[] labels() {
        return new String[]{"changeregion"};
    }

    @Override
    public String description() {
        return "Changes the server region to locked regions.";
    }

    @Override
    public String usage() {
        return "<region>";
    }
}
