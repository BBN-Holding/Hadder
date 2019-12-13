package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class ShutdownCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO).setTitle("Shutdown").build()).queue();
            event.getJDA().getShardManager().shutdown();
            System.out.println("Bot shut down via Command...");
            Runtime.getRuntime().exit(69);
        } else {
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"shutdown", "exit"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.owner.shutdown.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
