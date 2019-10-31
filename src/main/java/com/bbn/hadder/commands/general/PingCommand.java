package com.bbn.hadder.commands.general;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command {

    @Override
    public void executed(String[] args, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        event.getJDA().getRestPing().queue(ping -> {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("Ping").setDescription(String.valueOf(ping)).build()).queue();
        });
    }

    @Override
    public String[] labels() {
        return new String[]{"ping"};
    }

    @Override
    public String description() {
        return "Shows the ping to the discord api";
    }

    @Override
    public String usage() {
        return labels()[0];
    }
}
