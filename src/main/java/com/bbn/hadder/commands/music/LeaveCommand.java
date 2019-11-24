package com.bbn.hadder.commands.music;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;


public class LeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                    .setTitle("Successfully disconnected")
                    .setDescription("I successfully disconnected from the Voice Channel"))
                    .build()).queue();
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING, builder)
                    .setTitle("Not connected")
                    .setDescription("I'm currently in no Voice Channel on this Guild")
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"leave"};
    }

    @Override
    public String description() {
        return "Leaves a voice channel";
    }

    @Override
    public String usage() {
        return "";
    }
}
