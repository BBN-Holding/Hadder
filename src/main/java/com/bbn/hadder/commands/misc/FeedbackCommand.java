package com.bbn.hadder.commands.misc;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;

public class FeedbackCommand implements Command {
    
    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"feedback"};
    }

    @Override
    public String description() {
        return "Sends feedback directly to the developers.";
    }

    @Override
    public String usage() {
        return "<Feedback>";
    }
}
