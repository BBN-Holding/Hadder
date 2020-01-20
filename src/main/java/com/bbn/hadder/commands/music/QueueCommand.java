package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioInfo;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

import java.util.Set;

/**
 * @author Skidder / GregTCLTK
 */

public class QueueCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (!e.getAudioManager().hasPlayer(e.getGuild()) || e.getAudioManager().getTrackManager(e.getGuild()).getQueuedTracks().isEmpty()) {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.queue.error.title",
                    "commands.music.queue.error.description"
            ).build()).queue();
        } else {
            Set<AudioInfo> queue = e.getAudioManager().getTrackManager(e.getGuild()).getQueuedTracks();
            StringBuilder builder = new StringBuilder();
            long queuelength = 0;
            for (AudioInfo g : queue) {
                queuelength = queuelength + g.getTrack().getInfo().length;
                builder.append("("+e.getAudioManager().getTimestamp(g.getTrack().getInfo().length)+") **").append(g.getTrack().getInfo().author).append("**: `").append(g.getTrack().getInfo().title).append("` \n");
            }
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.queue.success.title", "("+String.valueOf(e.getAudioManager().getTimestamp(queuelength))+")",
                    "commands.music.queue.success.description", builder.toString())
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"queue"};
    }

    @Override
    public String description() {
        return "commands.music.queue.help.description";
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
