package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

/**
 * @author Skidder / GregTCLTK
 */

public class LoopCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"loop", "repeat"};
    }

    @Override
    public String description() {
        return "commands.music.loop.help.description";
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
