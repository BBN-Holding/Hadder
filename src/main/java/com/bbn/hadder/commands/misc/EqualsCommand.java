package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;

public class EqualsCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getChannel().sendMessage(
                new MessageEditor()
                        .setDefaultSettings(
                                MessageEditor.MessageType.INFO,
                                new EmbedBuilder()
                                        .setTitle("Please send me the first String")
                        ).build()
        ).queue();
        new EventWaiter().newOnMessageEventWaiter(msgevent -> {
            String firstString = msgevent.getMessage().getContentRaw();
            event.getChannel().sendMessage(
                    new MessageEditor()
                            .setDefaultSettings(
                                    MessageEditor.MessageType.INFO,
                                    new EmbedBuilder()
                                            .setTitle("Please send me the second String")
                            ).build()
            ).queue();
            new EventWaiter().newOnMessageEventWaiter(msgevent2 -> {
                String secondString = msgevent2.getMessage().getContentRaw();
                event.getChannel().sendMessage(
                        new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, new EmbedBuilder()
                                .setTitle((firstString.equals(secondString)) ? "Yes! The first string equals the second string!" : "Well yes, but actually No. This isn't the same.")
                                .addField("First String", firstString, false)
                                .addField("Second String", secondString, false)
                                .addField("Result", String.valueOf(firstString.equals(secondString)), false)
                        ).build()
                ).queue();
            }, event.getJDA(), event.getAuthor());
        }, event.getJDA(), event.getAuthor());
    }

    @Override
    public String[] labels() {
        return new String[]{"equals"};
    }

    @Override
    public String description() {
        return "Check if two strings are the same";
    }

    @Override
    public String usage() {
        return "";
    }
}
