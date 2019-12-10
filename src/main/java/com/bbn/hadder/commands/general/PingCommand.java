package com.bbn.hadder.commands.general;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class PingCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getJDA().getRestPing().queue(ping -> event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("Ping").setDescription(String.valueOf(ping)).build()).queue());
    }

    @Override
    public String[] labels() {
        return new String[]{"ping"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.general.ping.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
