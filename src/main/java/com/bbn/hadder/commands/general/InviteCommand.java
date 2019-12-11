package com.bbn.hadder.commands.general;

import com.bbn.hadder.Hadder;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

/*
 * @author Skidder / GregTCLTK
 */

public class InviteCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.general.invite.success.title"))
                .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.general.invite.success.description", "(https://discordapp.com/oauth2/authorize?client_id="  + Hadder.shardManager.getGuilds().get(0).getSelfMember().getId() + "&scope=bot&permissions=470133879)"))
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"invite"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.general.invite.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
