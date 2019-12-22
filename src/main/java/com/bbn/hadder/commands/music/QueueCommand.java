package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioInfo;
import com.bbn.hadder.audio.AudioManager;
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
        if (!new AudioManager().hasPlayer(event.getGuild()) || new AudioManager().getTrackManager(event.getGuild()).getQueuedTracks().isEmpty()) {
            event.getTextChannel().sendMessage(
                    event.getMessageEditor().getMessage(MessageEditor.MessageType.WARNING, "", "").build()).queue();
        } else {
            Set<AudioInfo> queue = new AudioManager().getTrackManager(event.getGuild()).getQueuedTracks();
            // Insert message here
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"queue"};
    }

    @Override
    public String description() {
        return "Shows the music queue.";
    }

    @Override
    public String usage() {
        return "";
    }
}
