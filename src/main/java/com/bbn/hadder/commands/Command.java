package com.bbn.hadder.commands;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.general.HelpCommand;

public interface Command {
    void executed(String[] args, CommandEvent event);
    HelpCommand.HelpInfo HELP_INFO();
}
