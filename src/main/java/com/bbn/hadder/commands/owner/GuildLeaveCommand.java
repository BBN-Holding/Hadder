package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Guild;

@Perms(Perm.BOT_OWNER)
public class GuildLeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            Guild guild = event.getJDA().getGuildById(args[0]);
            try {
                guild.leave().queue();
                event.getTextChannel()
                        .sendMessage(event.getMessageEditor()
                                .getMessage(MessageEditor.MessageType.INFO, "commands.owner.guildleave.success.title",
                                        "", "commands.owner.guildleave.success.description", guild.getName())
                                .build())
                        .queue();
            } catch (Exception e) {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.owner.guildleave.error.title", "", "commands.owner.guildleave.help.description", guild.getName()).build()).queue();
            }
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"guildleave"};
    }

    @Override
    public String description() {
        return "commands.owner.guildleave.help.description";
    }

    @Override
    public String usage() {
        return "[Guild-ID]";
    }

    @Override
    public String example() {
        return "366971954244354048";
    }
}
