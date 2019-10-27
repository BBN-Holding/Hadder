package com.bbn.hadder.core;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;

import java.util.ArrayList;

public class CommandHandler {

    public static ArrayList<Command> cmdlist = new ArrayList<>();

    public static void handleCommand(CommandParser.commandContainer cmd) {
        for (Command command : cmdlist) {
            for (String label : command.labels()) {
                if (label.equals(cmd.invoke)) command.executed(cmd.args, cmd.event);
            }

        }
    }
}
