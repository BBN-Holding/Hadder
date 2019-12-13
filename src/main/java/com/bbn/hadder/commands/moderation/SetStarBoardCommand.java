package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class SetStarBoardCommand implements Command {
    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMessage().getMentionedChannels().size()==1) {
            event.getRethink().setStarboardChannel(event.getGuild().getId(), event.getMessage().getMentionedChannels().get(0).getId());
            event.getChannel().sendMessage(new EmbedBuilder().setTitle("Successfully set the Channel!").build()).queue();
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"setstarboard"};
    }

    @Override
    public String description() {
        return "Sets the starboard channel";
    }

    @Override
    public String usage() {
        return "<channelid>";
    }
}
