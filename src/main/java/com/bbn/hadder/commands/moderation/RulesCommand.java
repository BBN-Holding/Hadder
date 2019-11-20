package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

public class RulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"rules"};
    }

    @Override
    public String description() {
        return "Setup the rules on your server";
    }

    @Override
    public String usage() {
        return "";
    }
}
