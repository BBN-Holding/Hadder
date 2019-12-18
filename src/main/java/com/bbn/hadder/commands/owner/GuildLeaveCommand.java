package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Guild;

public class GuildLeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (args.length > 0) {
                Guild guild = event.getJDA().getGuildById(args[0]);
                guild.leave().queue();
                event.getTextChannel()
                        .sendMessage(event.getMessageEditor()
                                .getMessage(MessageEditor.MessageType.INFO, "commands.owner.guildleave.success.title",
                                        "", "commands.owner.guildleave.success.description", guild.getName())
                                .build())
                        .queue();
            } else {
                event.getHelpCommand().sendHelp(this, event);
            }
        } else {
            event.getTextChannel()
                    .sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_PERMISSION).build())
                    .queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[] { "guildleave" };
    }

    @Override
    public String description() {
        return "commands.owner.guildleave.help.description";
    }

    @Override
    public String usage() {
        return "guildid";
    }
}
