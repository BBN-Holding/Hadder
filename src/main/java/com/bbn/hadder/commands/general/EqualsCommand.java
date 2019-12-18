package com.bbn.hadder.commands.general;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;

/*
 * @author Skidder / GregTCLTK
 */

public class EqualsCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getChannel().sendMessage(
                event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.general.equals.string.first.request", "")
                        .build()).queue();
        new EventWaiter().newOnMessageEventWaiter(msgevent -> {
            String firstString = msgevent.getMessage().getContentRaw();
            event.getChannel().sendMessage(
                    event.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.general.equals.string.second.request",
                            "").build()).queue();
            new EventWaiter().newOnMessageEventWaiter(msgevent2 -> {
                String secondString = msgevent2.getMessage().getContentRaw();
                event.getChannel().sendMessage(
                        event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                .setTitle((firstString.equals(secondString)) ? event.getMessageEditor().getTerm( "commands.general.equals.string.equals.true") : event.getMessageEditor().getTerm( "commands.general.equals.string.equals.false"))
                                .addField(event.getMessageEditor().getTerm( "commands.general.equals.string.first"), firstString, false)
                                .addField(event.getMessageEditor().getTerm( "commands.general.equals.string.second"), secondString, false)
                                .addField(event.getMessageEditor().getTerm( "commands.general.equals.string.result"), String.valueOf(firstString.equals(secondString)), false)
                                .build()).queue();
            }, event.getJDA(), event.getAuthor());
        }, event.getJDA(), event.getAuthor());
    }

    @Override
    public String[] labels() {
        return new String[]{"equals"};
    }

    @Override
    public String description() {
        return "commands.general.equals.help.description";
    }

    @Override
    public String usage() {
        return "";
    }
}
