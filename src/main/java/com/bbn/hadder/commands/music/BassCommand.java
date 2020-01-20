/*
  @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;

public class BassCommand implements Command {
    private static final float[] BASS_BOOST = { 0.2f, 0.15f, 0.1f, 0.05f, 0.0f, -0.05f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f };

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            if (e.getAudioManager().hasPlayer(e.getGuild()) && e.getAudioManager().getPlayer(e.getGuild()).getPlayingTrack() != null) {
                if (e.getMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().inVoiceChannel() && e.getGuild().getSelfMember().getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())) {
                    float value = Float.parseFloat(args[0]);
                    EqualizerFactory equalizer = new EqualizerFactory();
                    for (int i = 0; i < BASS_BOOST.length; i++) {
                        equalizer.setGain(i, BASS_BOOST[i] + value);
                    }
                    e.getAudioManager().getPlayer(e.getGuild()).setFrameBufferDuration(500);
                    e.getAudioManager().getPlayer(e.getGuild()).setFilterFactory(equalizer);
                } else {
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                            "commands.music.bass.error.connected.title",
                            "commands.music.bass.error.connected.description")
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
        return new String[]{"bass"};
    }

    @Override
    public String description() {
        return "commands.music.bass.help.description";
    }

    @Override
    public String usage() {
        return "[Bass-Level]";
    }

    @Override
    public String example() {
        return "1000";
    }
}
