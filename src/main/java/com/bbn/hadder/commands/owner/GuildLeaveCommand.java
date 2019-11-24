package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

public class GuildLeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (args.length > 0) {
                Guild guild = event.getJDA().getGuildById(args[0]);
                guild.leave().queue();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                        .setTitle("Successfully left")
                        .setDescription("I successfully left " + guild.getName()))
                        .build()).queue();
            } else {
                event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"guildleave"};
    }

    @Override
    public String description() {
        return "Quit from a guild";
    }

    @Override
    public String usage() {
        return "<Guild-ID>";
    }
}
