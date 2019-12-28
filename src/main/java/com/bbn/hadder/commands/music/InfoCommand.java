package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * @author Skidder / GregTCLTK
 */

public class InfoCommand implements Command {

    private static final String CD = "\uD83D\uDCBF";
    private static final String MIC = "\uD83C\uDFA4";
    private static final String QUEUE_DESCRIPTION = "%s **|>**  %s\n%s\n%s %s\n%s";

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getAudioManager().hasPlayer(event.getGuild()) && event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack() == null) {
            AudioTrack track = event.getAudioManager().getPlayer(event.getGuild()).getPlayingTrack();
            event.getTextChannel().sendMessage("Track Info" + String.format(QUEUE_DESCRIPTION, CD, event.getAudioManager().getOrNull(track.getInfo().title),
                    "\n\u23F1 **|>** `[ " + event.getAudioManager().getTimestamp(track.getPosition()) + " / " + event.getAudioManager().getTimestamp(track.getInfo().length) + " ]`",
                    "\n" + MIC, event.getAudioManager().getOrNull(track.getInfo().author),
                    "\n\uD83C\uDFA7 **|>**  " + "")).queue();
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
        return "Shows information about the playing song";
    }

    @Override
    public String usage() {
        return "";
    }
}
