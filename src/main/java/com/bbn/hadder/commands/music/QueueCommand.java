package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioInfo;
import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Set;

/**
 * @author Skidder / GregTCLTK
 */

public class QueueCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (!new AudioManager().hasPlayer(event.getGuild()) || new AudioManager().getTrackManager(event.getGuild()).getQueuedTracks().isEmpty()) {
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.WARNING,
                    "commands.music.queue.error.title",
                    "commands.music.queue.error.description"
            ).build()).queue();
        } else {
            Set<AudioInfo> queue = new AudioManager().getTrackManager(event.getGuild()).getQueuedTracks();
            EmbedBuilder b = event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.queue.success.title",
                    "commands.music.queue.success.description")
                    .addField("Queued songs", String.valueOf(queue.size()), true);
            for (AudioInfo g : queue) {
                b.addField(g.getTrack().getInfo().author, g.getTrack().getInfo().title, true);
            }
            event.getTextChannel().sendMessage(b.build()).queue();
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
