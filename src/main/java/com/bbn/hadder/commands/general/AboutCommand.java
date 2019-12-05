package com.bbn.hadder.commands.general;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

public class AboutCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"about", "info"};
    }

    @Override
    public String description() {
        return "Shows infos about Hadder";
    }

    @Override
    public String usage() {
        return "";
    }
}
