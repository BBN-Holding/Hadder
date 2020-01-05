package com.bbn.hadder.commands.music;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class PauseCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getAudioManager().hasPlayer(event.getGuild()) && event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack() != null) {
            if (event.getMember().getVoiceState().inVoiceChannel() && event.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
                if (!event.getAudioManager().getPlayer(event.getGuild()).isPaused()) {
                    event.getAudioManager().getPlayer(event.getGuild()).setPaused(true);
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.pause.success.title",
                            "commands.music.pause.success.description")
                            .build()).queue();
                } else {
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                            "commands.music.pause.error.paused.title",
                            "commands.music.pause.error.paused.description")
                            .build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.pause.error.connected.title",
                        "commands.music.pause.error.connected.description")
                        .build()).queue();
            }
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.info.error.title",
                    "commands.music.info.error.description").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"pause"};
    }

    @Override
    public String description() {
        return "commands.music.pause.help.description";
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
