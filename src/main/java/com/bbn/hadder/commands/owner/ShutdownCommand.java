package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShutdownCommand implements Command {
    @Override
    public void executed(String[] args, MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("477141528981012511") || event.getAuthor().getId().equals("261083609148948488")) {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("Shutdown").build()).queue();
            event.getJDA().getShardManager().shutdown();
            System.out.println("Bot shut down via Command...");
            System.exit(0);
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"shutdown"};
    }
}
