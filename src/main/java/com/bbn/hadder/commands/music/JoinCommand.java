package com.bbn.hadder.commands.music;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;


public class JoinCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().getVoiceState().inVoiceChannel()) {
            AudioManager audioManager = event.getGuild().getAudioManager();
            if(!audioManager.isAttemptingToConnect()) {
                VoiceChannel vc = event.getMember().getVoiceState().getChannel();
                if (!event.getGuild().getSelfMember().getVoiceState().getChannel().getId().equals(vc.getId())) {
                    event.getGuild().getAudioManager().openAudioConnection(vc);
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle("Successfully connected")
                            .setDescription("I successfully connected to " + vc.getName() + ".")
                            .build()).queue();
                    } else {
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                            .setTitle("Already connected")
                            .setDescription("I am already connected to your voice channel.")
                            .build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                        .setTitle("Already trying to connect")
                        .setDescription("Hadder is already trying to connect. Please wait a moment.")
                        .build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.WARNING)
                    .setTitle("No Voice Channel")
                    .setDescription("You aren't in a Voice Channel.")
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"join"};
    }

    @Override
    public String description() {
        return "Joins your voice channel";
    }

    @Override
    public String usage() {
        return "";
    }
}
