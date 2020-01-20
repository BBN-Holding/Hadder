package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.managers.AudioManager;

/*
 * @author Skidder / GregTCLTK
 */

public class JoinCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getMember().getVoiceState().inVoiceChannel()) {
            AudioManager audioManager = e.getGuild().getAudioManager();
            if(!audioManager.isAttemptingToConnect()) {
                VoiceChannel vc = e.getMember().getVoiceState().getChannel();
                if (e.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
                    if (!e.getGuild().getSelfMember().getVoiceState().getChannel().getId().equals(vc.getId())) {
                        try {
                            e.getGuild().getAudioManager().openAudioConnection(vc);
                            e.getTextChannel().sendMessage(
                                    e.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.INFO,
                                            "commands.music.join.success.title",
                                            "",
                                            "commands.music.join.success.description",
                                            vc.getName())
                                            .build()).queue();
                        } catch (InsufficientPermissionException ex) {
                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                    "commands.music.join.error.permission.title",
                                    "commands.music.join.error.permission.description")
                                    .build()).queue();
                        }
                    } else {
                        e.getTextChannel().sendMessage(
                            e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.WARNING,
                                "commands.music.join.error.connecting.already.title",
                                "commands.music.join.error.connecting.already.description")
                                .build()).queue();
                    }
                } else {
                    try {
                        e.getGuild().getAudioManager().openAudioConnection(vc);
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.music.join.success.title", "",
                                "commands.music.join.success.description", vc.getName())
                                .build()).queue();
                    } catch (InsufficientPermissionException ex) {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                "commands.music.join.error.permission.title",
                                "commands.music.join.error.permission.description")
                                .build()).queue();
                    }
                }
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                    MessageEditor.MessageType.WARNING,
                    "commands.music.join.error.connecting.trying.title",
                    "commands.music.join.error.connecting.trying.description")
                    .build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                MessageEditor.MessageType.ERROR,
                "commands.music.join.error.channel.title",
                "commands.music.join.error.channel.description")
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"join"};
    }

    @Override
    public String description() {
        return "commands.music.join.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
