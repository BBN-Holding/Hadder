package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;

public class PrefixCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (args.length == 1) {
                if (!args[0].contains("\"")) {

                    event.getRethink().setGuildPrefix(args[0], event.getGuild().getId());
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.moderation.prefix.success.title",
                            "✅",
                            "commands.moderation.prefix.success.description",
                            args[0]).build()
                    ).queue();
                } else {
                    event.getTextChannel().sendMessage(
                            event.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.WARNING,
                                    "",
                                    "commands.moderation.prefix.error.description").build()).queue();
                }
            } else {
                event.getHelpCommand().sendHelp(this, event);
            }
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }

    @Override
    public String description() {
        return "commands.moderation.prefix.help.description";
    }

    @Override
    public String usage() {
        return "prefix";
    }
}
