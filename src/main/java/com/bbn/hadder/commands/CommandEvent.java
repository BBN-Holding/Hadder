package com.bbn.hadder.commands;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.commands.general.HelpCommand;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.core.Config;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class CommandEvent extends MessageReceivedEvent {

    private Rethink rethink;
    private Config config;
    private CommandHandler commandHandler;
    private HelpCommand helpCommand;
    private MessageEditor messageEditor;

    public CommandEvent(@Nonnull JDA api, long responseNumber, @Nonnull Message message, Rethink rethink, Config config, CommandHandler commandHandler, HelpCommand helpCommand, MessageEditor messageEditor) {
        super(api, responseNumber, message);
        this.rethink = rethink;
        this.config = config;
        this.commandHandler = commandHandler;
        this.helpCommand = helpCommand;
        this.messageEditor = messageEditor;
    }

    public Rethink getRethink() {
        return rethink;
    }

    public Config getConfig() {
        return config;
    }

    public HelpCommand getHelpCommand() {
        return helpCommand;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public MessageEditor getMessageEditor() {
        return messageEditor;
    }
}
