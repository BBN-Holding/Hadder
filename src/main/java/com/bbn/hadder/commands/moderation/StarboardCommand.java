package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.TextChannel;

public class StarboardCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getMessage().getMentionedChannels().size()==1) {
            e.getRethink().setStarboardChannel(e.getGuild().getId(), e.getMessage().getMentionedChannels().get(0).getId());
            e.getChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.starboard.success.title","")
                            .build())
                    .queue();
        } else {
            if (args.length>0) {
                TextChannel channel = e.getGuild().getTextChannelById(args[0]);
                if (channel!=null) {
                    e.getRethink().setStarboardChannel(e.getGuild().getId(), channel.getId());
                }
            } else {
                e.getHelpCommand().sendHelp(this, e);
            }
        }

        if (args.length==2) {
            e.getRethink().setNeededStars(args[1], e.getGuild().getId());
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"starboard"};
    }

    @Override
    public String description() {
        return "commands.moderation.starboard.help.description";
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
