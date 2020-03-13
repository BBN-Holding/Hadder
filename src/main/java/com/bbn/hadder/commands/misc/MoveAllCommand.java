/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.Objects;

public class MoveAllCommand implements Command {

    @Perms(Perm.VOICE_MOVE_OTHERS)
    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 2) {
            Objects.requireNonNull(e.getGuild().getVoiceChannelById(args[0])).getMembers().forEach(
                    member -> {
                        e.getGuild().moveVoiceMember(member, e.getGuild().getVoiceChannelById(args[1])).queue();
                    }
            );
            e.getChannel().sendMessage(new EmbedBuilder().setTitle("Successfully Moved!").setDescription("I moved " +
                    Objects.requireNonNull(e.getGuild().getVoiceChannelById(args[0])).getMembers().size() + " Members. Have fun!").build()).queue();
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"moveall", "move-all"};
    }

    @Override
    public String description() {
        return "Moves All users in channel1 to channel2";
    }

    @Override
    public String usage() {
        return "[channel1] [channel2]";
    }

    @Override
    public String example() {
        return "452806287307046923 452858405212782623";
    }
}
