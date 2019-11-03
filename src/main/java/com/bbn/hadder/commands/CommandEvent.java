package com.bbn.hadder.commands;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.core.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class CommandEvent extends MessageReceivedEvent {

    private Rethink rethink;
    private Config config;
    private CommandHandler commandHandler;

    public CommandEvent(@Nonnull JDA api, long responseNumber, @Nonnull Message message, Config config, Rethink rethink, CommandHandler commandHandler) {
        super(api, responseNumber, message);
        this.config = config;
        this.rethink = rethink;
        this.commandHandler = commandHandler;
    }

    public CommandEvent(@Nonnull MessageReceivedEvent event, Config config, Rethink rethink, CommandHandler commandHandler) {
        super(event.getJDA(), event.getResponseNumber(), event.getMessage());
        this.config = config;
        this.rethink = rethink;
        this.commandHandler = commandHandler;
    }

    public Rethink getRethink() {
        return rethink;
    }

    public Config getConfig() {
        return config;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
