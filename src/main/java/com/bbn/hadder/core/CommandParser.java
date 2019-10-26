package com.bbn.hadder.core;

/*
 * @author Skidder / GregTCLTK
 */

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;

public class CommandParser {
    public static commandContainer parser(String raw, MessageReceivedEvent event) {

        String cmd = raw.replaceFirst("h.", "");
        String[] cmdsplit = cmd.split(" ");
        String invoke = cmdsplit[0];
        ArrayList<String> split = new ArrayList<>();
        Collections.addAll(split, cmdsplit);
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new commandContainer(raw, cmd, cmdsplit, invoke, args, event);
    }

    static class commandContainer {

        final String raw;
        final String cmd;
        final String[] cmdsplit;
        final String invoke;
        final String[] args;
        final MessageReceivedEvent event;

        commandContainer(String rw, String cmd, String[] cmdsplit, String invoke, String[] args, MessageReceivedEvent event) {
            this.raw = rw;
            this.cmd = cmd;
            this.cmdsplit = cmdsplit;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }

    }
}
