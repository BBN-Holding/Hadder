package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;

@Perms(Perm.BOT_OWNER)
public class RebootCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        Runtime.getRuntime().exit(69);
    }

    @Override
    public String[] labels() {
        return new String[]{"restart", "restart"};
    }

    @Override
    public String description() {
        return "commands.owner.reboot.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
