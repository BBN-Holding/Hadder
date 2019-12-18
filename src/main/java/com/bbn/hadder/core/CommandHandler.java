package com.bbn.hadder.core;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.commands.Perm;
import com.bbn.hadder.commands.Perms;
import com.bbn.hadder.commands.general.HelpCommand;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    private List<Command> commandList;
    private Config config;
    private HelpCommand helpCommand;

    public CommandHandler(List<Command> commandList, Config config, HelpCommand helpCommand) {
        this.commandList = commandList;
        this.config = config;
        this.helpCommand = helpCommand;
    }

    public void handle(MessageReceivedEvent event, Rethink rethink, String prefix) {
        String invoke = event.getMessage().getContentRaw().replaceFirst(prefix, "").split(" ")[0];
        for (Command cmd : commandList) {
            for (String label : cmd.labels()) {
                if (label.equals(invoke)) {
                    String argString = event.getMessage().getContentRaw()
                            .replaceFirst(prefix, "").replaceFirst(invoke, "");
                    if (argString.startsWith(" ")) argString = argString.replaceFirst(" ", "");
                    String[] args = argString.split(" ");
                    if (args.length > 0 && args[0].equals("")) args = new String[0];

                    CommandEvent commandEvent = new CommandEvent(event.getJDA(), event.getResponseNumber(), event.getMessage(), rethink,
                            config, this, helpCommand, new MessageEditor(rethink, event.getAuthor()));

                    if (!Arrays.asList(cmd.getClass().getAnnotations()).contains(Perms.class)) {
                        for (Perm perm : cmd.getClass().getAnnotation(Perms.class).value()) {
                            if (!perm.check(commandEvent)) {
                                commandEvent.getTextChannel()
                                        .sendMessage(commandEvent.getMessageEditor().getMessage(MessageEditor.MessageType.NO_PERMISSION).build())
                                        .queue();
                                return;
                            }
                        }
                    }

                    cmd.executed(args, commandEvent);
                    return;
                }
            }
        }
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
