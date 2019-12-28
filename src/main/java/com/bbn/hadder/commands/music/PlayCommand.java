package com.bbn.hadder.commands.music;

import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Message;

import java.net.URL;

/**
 * @author Skidder / GregTCLTK
 */

public class PlayCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getMember().getVoiceState().inVoiceChannel()) {
                String input = event.getMessage().getContentRaw().replaceFirst(event.getRethink().getGuildPrefix(event.getGuild().getId()) + "play ", "").replaceFirst(event.getRethink().getUserPrefix(event.getAuthor().getId()) + "play ", "");
                try {
                    new URL(input).toURI();
                    Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.play.load.title", "⭕",
                            "commands.music.play.load.description", "").build()).complete();
                    event.getAudioManager().loadTrack(input, event, msg);
                } catch (Exception ignore) {
                    Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.play.load.title", "⭕",
                            "commands.music.play.load.description", "").build()).complete();
                    event.getAudioManager().loadTrack("ytsearch: " + input, event, msg);
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.WARNING,
                        "commands.music.join.error.channel.title",
                        "commands.music.join.error.channel.description")
                        .build()).queue();
            }
        } else event.getHelpCommand().sendHelp(this, event);
    }

    @Override
    public String[] labels() {
        return new String[]{"play"};
    }

    @Override
    public String description() {
        return "commands.music.play.help.description";
    }

    @Override
    public String usage() {
        return "song";
    }
}
