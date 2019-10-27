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
        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("Ping").setDescription("0").build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"ping"};
    }
}
