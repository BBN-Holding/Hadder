package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * @author Skidder / GregTCLTK
 */

public class InfoCommand implements Command {

    private static final String CD = "\uD83D\uDCBF";
    private static final String MIC = "\uD83C\uDFA4";

    private static final String QUEUE_TITLE = "__%s has added %d new track%s to the Queue:__";
    private static final String QUEUE_DESCRIPTION = "%s **|>**  %s\n%s\n%s %s\n%s";
    private static final String QUEUE_INFO = "Info about the Queue: (Size - %d)";
    private static final String ERROR = "Error while loading \"%s\"";
    @Override
    public void executed(String[] args, CommandEvent event) {
        if (!new AudioManager().hasPlayer(event.getGuild()) || new AudioManager().getPlayer(event.getGuild()).getPlayingTrack() == null) {
            event.getTextChannel().sendMessage("No song is being played at the moment! *It's your time to shine..*").queue();
        } else {
            AudioTrack track = new AudioManager().getPlayer(event.getGuild()).getPlayingTrack();
            event.getTextChannel().sendMessage("Track Info" + String.format(QUEUE_DESCRIPTION, CD, new AudioManager().getOrNull(track.getInfo().title),
                    "\n\u23F1 **|>** `[ " + getTimestamp(track.getPosition()) + " / " + getTimestamp(track.getInfo().length) + " ]`",
                    "\n" + MIC, new AudioManager().getOrNull(track.getInfo().author),
                    "\n\uD83C\uDFA7 **|>**  " + "")).queue();
        }
    }

    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    @Override
    public String[] labels() {
        return new String[]{"info"};
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
