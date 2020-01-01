package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.TextChannel;

public class StarboardCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMessage().getMentionedChannels().size()==1) {
            event.getRethink().setStarboardChannel(event.getGuild().getId(), event.getMessage().getMentionedChannels().get(0).getId());
            event.getChannel().sendMessage(
                    event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.starboard.success.title","")
                            .build())
                    .queue();
        } else {
            if (args.length>0) {
                TextChannel channel = event.getGuild().getTextChannelById(args[0]);
                if (channel!=null) {
                    event.getRethink().setStarboardChannel(event.getGuild().getId(), channel.getId());
                }
            } else {
                event.getHelpCommand().sendHelp(this, event);
            }
        }

        if (args.length==2) {
            event.getRethink().setNeededStars(args[1], event.getGuild().getId());
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"starboard"};
    }

    @Override
    public String description() {
        return "Sets the starboard channel";
    }

    @Override
    public String usage() {
        return "[Channel] [Needed stars]";
    }

    @Override
    public String example() {
        return "#starboard 4";
    }
}
