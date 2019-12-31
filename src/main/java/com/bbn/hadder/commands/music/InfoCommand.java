package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * @author Skidder / GregTCLTK
 */

public class InfoCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getAudioManager().hasPlayer(event.getGuild()) && event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack() != null) {
            AudioTrack track = event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack();
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.info.success.title", "")
                    .setAuthor(track.getInfo().author)
                    .addField("Title", track.getInfo().title, true)
                    .addField("Progress", "`[ " + event.getAudioManager().getTimestamp(track.getPosition()) + " / " + event.getAudioManager().getTimestamp(track.getInfo().length) + " ]`", false)
                    .build()).queue();
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.info.error.title",
                    "commands.music.info.error.description").build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"info", "songinfo"};
    }

    @Override
    public String description() {
        return "commands.music.info.help.description";
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
