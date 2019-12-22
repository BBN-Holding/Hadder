package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

/**
 * @author Skidder / GregTCLTK
 */

public class StopCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"stop"};
    }

    @Override
    public String description() {
        return "Stops the song";
    }

    @Override
    public String usage() {
        return "";
    }
}
