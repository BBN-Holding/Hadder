package com.bbn.hadder.core;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandHandler {

    List<Command> commandList;
    Config config;

    public CommandHandler(List<Command> commandList, Config config) {
        this.commandList = commandList;
        this.config = config;
    }

    public void handle(MessageReceivedEvent event, Rethink rethink, String prefix) {
        StringBuilder regexBuilder = new StringBuilder().append("\\").append(prefix);
        String invoke = event.getMessage().getContentRaw().replaceFirst(prefix, "").split(" ")[0];
        for (Command cmd : commandList) {
            for (String label : cmd.labels()) {
                if (label.equals(invoke)) {
                    String argString = event.getMessage().getContentRaw()
                            .replaceFirst(prefix, "").replaceFirst(invoke, "");
                    if (argString.startsWith(" ")) argString = argString.replaceFirst(" ", "");
                    String[] args = argString.split(" ");
                    if (args.length>0&&args[0].equals("")) args = new String[0];
                    cmd.executed(args, new CommandEvent(event, config, rethink, this));
                    return;
                }
            }
        }
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
