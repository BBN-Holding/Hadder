package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.commands.Perm;
import com.bbn.hadder.commands.Perms;
import com.bbn.hadder.utils.MessageEditor;

@Perms(Perm.BOT_OWNER)
public class ShutdownCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO).setTitle("Shutdown").build()).queue();
        event.getJDA().getShardManager().shutdown();
        System.out.println("Bot shut down via Command...");
        Runtime.getRuntime().exit(69);
    }

    @Override
    public String[] labels() {
        return new String[]{"shutdown", "exit"};
    }

    @Override
    public String description() {
        return "commands.owner.shutdown.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
