package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class LoopCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getAudioManager().hasPlayer(e.getGuild()) && e.getAudioManager().getPlayer(e.getGuild()).getPlayingTrack() != null) {
            if (e.getMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())) {
                if (e.getAudioManager().getTrackManager(e.getGuild()).isLoop()) {
                    e.getAudioManager().getTrackManager(e.getGuild()).setLoop(false);
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.music.loop.success.unloop.title", "commands.music.loop.success.unloop.description").build()).queue();
                } else {
                    e.getAudioManager().getTrackManager(e.getGuild()).setLoop(true);
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.music.loop.success.loop.title", "commands.music.loop.success.loop.description").build()).queue();
                }
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.loop.error.connected.title",
                        "commands.music.loop.error.connected.description")
                        .build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
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
