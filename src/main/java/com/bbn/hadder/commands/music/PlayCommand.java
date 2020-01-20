package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import java.net.URL;

/**
 * @author Skidder / GregTCLTK
 */

public class PlayCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length > 0) {
            if (event.getMember().getVoiceState().inVoiceChannel()) {
                String input = event.getMessage().getContentRaw().replaceFirst(event.getRethinkServer().getPrefix() + "play ", "").replaceFirst(event.getRethinkUser().getPrefix() + "play ", "");
                try {
                    new URL(input).toURI();
                    Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.play.load.title", "⭕",
                            "commands.music.play.load.description", "").build()).complete();
                    event.getAudioManager().loadTrack(input, event, msg);
                } catch (InsufficientPermissionException e) {
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                            "commands.music.join.error.permission.title",
                            "commands.music.join.error.permission.description")
                            .build()).queue();
                } catch (Exception ignore) {
                    Message msg = event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.music.play.load.title", "⭕",
                            "commands.music.play.load.description", "").build()).complete();
                    event.getAudioManager().loadTrack("ytsearch: " + input, event, msg);
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.ERROR,
                        "commands.music.join.error.channel.title",
                        "commands.music.join.error.channel.description")
                        .build()).queue();
            }
        } else if (event.getAudioManager().getPlayer(event.getGuild()).isPaused()) {
            if (event.getMember().getVoiceState().inVoiceChannel() && event.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
                event.getAudioManager().getPlayer(event.getGuild()).setPaused(false);
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.music.play.success.unpause.title",
                        "commands.music.play.success.unpause.description")
                        .build()).queue();
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.play.error.connected.title",
                        "commands.music.play.error.connected.description")
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
        return "[Song URL/Name]";
    }

    @Override
    public String example() {
        return "Last Christmas";
    }
}
