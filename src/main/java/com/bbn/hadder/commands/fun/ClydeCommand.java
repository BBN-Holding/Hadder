package com.bbn.hadder.commands.fun;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.commands.Perm;
import com.bbn.hadder.commands.Perms;

@Perms(Perm.MANAGE_SERVER)
public class ClydeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {

    }

    @Override
    public String[] labels() {
        return new String[]{"clyde"};
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public String usage() {
        return "<Message-Content>";
    }
}
