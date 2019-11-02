package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class ScreenshareCommand implements Command {
    @Override
    public void executed(String[] args, MessageReceivedEvent event) {
        if (args.length>0) {
            if (args[0].matches("[0-9]*")&&args.length==1) {
                for (VoiceChannel vc : event.getGuild().getVoiceChannels()) {
                    try {
                        if (vc.getIdLong() == Long.parseLong(args[0])) {
                            event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, new EmbedBuilder()
                                    .setTitle("Here's your Url to share your Screen")
                                    .setDescription("http://discordapp.com/channels/" + event.getGuild().getId() + "/" + vc.getId() + "/")).build()).queue();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.ERROR, new EmbedBuilder()
                                .setTitle("Wait, thats illegal")
                                .setDescription("This Id is invalid. \nMaybe you entered a wrong Id? \n\nNote: Make sure the Voicechannel is on this Guild.")).build()).queue();
                        return;
                    }
                }
            } else {
                List<VoiceChannel> vcs = event.getGuild().getVoiceChannelsByName(String.join(" ", args), true);
                if (vcs.size() > 1) {
                    EmbedBuilder eb = new EmbedBuilder()
                            .setTitle("Please Choose a Voicechannel")
                            .setDescription("There is more than one channel with this name");
                    for (int i = 0; i < vcs.size(); i++) {
                        VoiceChannel voiceChannel = vcs.get(i);
                        eb.addField(i + ": " + voiceChannel.getName(), voiceChannel.getId(), false);
                    }
                    event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, eb).build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(msgevent -> {
                        try {
                            int i = Integer.parseInt(msgevent.getMessage().getContentRaw());
                            if (vcs.size() > i) {
                                event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, new EmbedBuilder()
                                        .setTitle("Here's your Url to share your Screen")
                                        .setDescription("http://discordapp.com/channels/" + event.getGuild().getId() + "/" + vcs.get(i).getId() + "/")).build()).queue();
                            } else {

                                event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.ERROR, new EmbedBuilder()
                                        .setTitle("You specified a wrong number!")).build()).queue();
                            }
                        } catch (NumberFormatException e) {
                            event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.ERROR, new EmbedBuilder()
                                    .setTitle("Wait, thats illegal")
                                    .setDescription("This isn't a Number.")).build()).queue();
                        }
                    }, event.getJDA(), event.getAuthor());
                } else if (vcs.size()==0) {
                    event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.ERROR, new EmbedBuilder()
                            .setTitle("Hol' up")
                            .setDescription("There is no Voicechannel named like this. \n\nNote: Make sure the Voicechannel is on this Guild.")).build()).queue();
                } else {
                    event.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, new EmbedBuilder()
                            .setTitle("Here's your Url to share your Screen")
                            .setDescription("http://discordapp.com/channels/" + event.getGuild().getId() + "/" + vcs.get(0).getId() + "/")).build()).queue();
                }
            }
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"screenshare"};
    }

    @Override
    public String description() {
        return "Give you a link with the screenshare url";
    }

    @Override
    public String usage() {
        return "screenshare <voicechannelid|voicechannelname>";
    }
}
