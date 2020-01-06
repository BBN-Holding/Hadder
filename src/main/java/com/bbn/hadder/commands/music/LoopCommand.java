package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class LoopCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getAudioManager().hasPlayer(event.getGuild()) && event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack() != null) {
            if (event.getMember().getVoiceState().inVoiceChannel() && event.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
                if (event.getAudioManager().getTrackManager(event.getGuild()).isLoop()) {
                    event.getAudioManager().getTrackManager(event.getGuild()).setLoop(false);
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.music.loop.success.loop.title", "commands.music.loop.success.loop.description").build()).queue();
                } else {
                    event.getAudioManager().getTrackManager(event.getGuild()).setLoop(true);
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.music.loop.success.unloop.title", "commands.music.loop.success.unloop.description").build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.loop.error.connected.title",
                        "commands.music.loop.error.connected.description")
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
        return new String[]{"loop", "repeat"};
    }

    @Override
    public String description() {
        return "commands.music.loop.help.description";
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
