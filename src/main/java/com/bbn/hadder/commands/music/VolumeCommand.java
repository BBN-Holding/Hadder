package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/**
 * @author Skidder / GregTCLTK
 */

public class VolumeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            if (e.getAudioManager().hasPlayer(e.getGuild()) && e.getAudioManager().getPlayer(e.getGuild()).getPlayingTrack() != null) {
                if (e.getMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())) {
                    try {
                        int volume = Integer.parseInt(args[0]);
                        if (volume < 201 && volume > 0 || e.getConfig().getOwners().contains(e.getAuthor().getIdLong())) {
                            e.getAudioManager().getPlayer(e.getGuild()).setVolume(volume);
                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                                    "commands.music.volume.success.title", "",
                                    "commands.music.volume.success.description", String.valueOf(volume)).build()).queue();
                        } else {
                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                    "commands.music.volume.error.int.title",
                                    "commands.music.volume.error.int.description").build()).queue();
                        }
                    } catch (NumberFormatException ex) {
                        e.getHelpCommand().sendHelp(this, e);
                    }
                } else {
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                            "commands.music.volume.error.connected.title",
                            "commands.music.volume.error.connected.description")
                            .build()).queue();
                }
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.music.info.error.title",
                        "commands.music.info.error.description").build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"volume"};
    }

    @Override
    public String description() {
        return "commands.music.volume.help.description";
    }

    @Override
    public String usage() {
        return "[New volume]";
    }

    @Override
    public String example() {
        return "100";
    }
}
