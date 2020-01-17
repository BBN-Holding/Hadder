/*
  @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;

public class BassCommand implements Command {
    private static final float[] BASS_BOOST = { 0.2f, 0.15f, 0.1f, 0.05f, 0.0f, -0.05f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f };

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            float value = Float.parseFloat(args[0]);
            EqualizerFactory equalizer = new EqualizerFactory();
            for (int i = 0; i < BASS_BOOST.length; i++) {
                equalizer.setGain(i, BASS_BOOST[i] + value);
            }
            event.getAudioManager().getPlayer(event.getGuild()).setFrameBufferDuration(500);
            event.getAudioManager().getPlayer(event.getGuild()).setFilterFactory(equalizer);
            event.getTextChannel().sendMessage("Bruh, set dae bass").queue();
        }
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
