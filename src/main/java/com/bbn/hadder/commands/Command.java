package com.bbn.hadder.commands;

/*
 * @author Skidder / GregTCLTK
 */

public interface Command {
    void executed(String[] args, CommandEvent event);

    String[] labels();

    String description();

    String usage();

    String example();
}
