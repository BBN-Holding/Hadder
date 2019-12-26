package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

@Perms(Perm.MANAGE_SERVER)
public class EditRulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getRethink().getRulesMID(event.getGuild().getId()).length() == 18) {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.moderation.editrules.message.title",
                    "commands.moderation.editrules.message.description").build()).queue();
            event.getEventWaiter().newOnMessageEventWaiter(event1 -> {

            }, event);
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.editrules.error.title", "",
                    "commands.moderation.editrules.error.description", event.getRethink().getGuildPrefix(event.getGuild().getId())).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"editrules", "rulesedit", "edit_rules", "rules_edit"};
    }

    @Override
    public String description() {
        return "commands.moderation.editrules.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
