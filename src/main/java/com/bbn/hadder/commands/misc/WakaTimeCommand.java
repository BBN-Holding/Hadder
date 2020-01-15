package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

/**
 * @author Skidder / GregTCLTK
 */

public class WakaTimeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"wakatime", "wk"};
    }

    @Override
    public String description() {
        return "Show WakaTime stats.";
    }

    @Override
    public String usage() {
        return "[WakaTime-User]";
    }

    @Override
    public String example() {
        return "@Skidder";
    }
}
