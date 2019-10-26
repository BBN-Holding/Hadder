package com.bbn.hadder.core;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;

import java.util.HashMap;

public class CommandHandler {

    public static HashMap<String, Command> cmdlist = new HashMap<>();

    public static void handleCommand(CommandParser.commandContainer cmd) {
        if(cmdlist.containsKey(cmd.invoke)) {
            cmdlist.get(cmd.invoke).executed(cmd.event);
        }

    }
}
