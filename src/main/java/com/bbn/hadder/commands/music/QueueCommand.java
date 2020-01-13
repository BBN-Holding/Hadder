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
    public void executed(String[] args, CommandEvent event) {
        if (!event.getAudioManager().hasPlayer(event.getGuild()) || event.getAudioManager().getTrackManager(event.getGuild()).getQueuedTracks().isEmpty()) {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.queue.error.title",
                    "commands.music.queue.error.description"
            ).build()).queue();
        } else {
            Set<AudioInfo> queue = event.getAudioManager().getTrackManager(event.getGuild()).getQueuedTracks();
            StringBuilder builder = new StringBuilder();
            long queuelength = 0;
            for (AudioInfo g : queue) {
                queuelength = queuelength + g.getTrack().getInfo().length;
                builder.append("("+event.getAudioManager().getTimestamp(g.getTrack().getInfo().length)+") **").append(g.getTrack().getInfo().author).append("**: `").append(g.getTrack().getInfo().title).append("` \n");
            }
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.queue.success.title", "("+String.valueOf(event.getAudioManager().getTimestamp(queuelength))+")",
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
