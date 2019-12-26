package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import com.bbn.hadder.utils.MessageEditor.MessageType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;

public class ScreenShareCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length>0) {
            if (args[0].matches("[0-9]*")&&args.length==1) {
                for (VoiceChannel vc : event.getGuild().getVoiceChannels()) {
                    try {
                        if (vc.getIdLong() == Long.parseLong(args[0])) {
                            event.getChannel().sendMessage(event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.misc.screenshare.success.title", "")
                                    .setDescription("http://discordapp.com/channels/" + event.getGuild().getId() + "/" + vc.getId() + "/").build()).queue();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.id.error.title", "commands.misc.screenshare.id.error.description").build()).queue();
                        event.getHelpCommand().sendHelp(this, event);
                        return;
                    }
                }
            } else {
                List<VoiceChannel> vcs = event.getGuild().getVoiceChannelsByName(String.join(" ", args), true);
                if (vcs.size() > 1) {
                    EmbedBuilder eb = event.getMessageEditor().getMessage(MessageType.ERROR, "commands.misc.screenshare.channel.error.title", "commands.misc.screenshare.channel.error.description");
                    for (int i = 0; i < vcs.size(); i++) {
                        VoiceChannel voiceChannel = vcs.get(i);
                        eb.addField(i + ": " + voiceChannel.getName(), voiceChannel.getId(), false);
                    }
                    event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.WARNING).build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(msgevent -> {
                        try {
                            int i = Integer.parseInt(msgevent.getMessage().getContentRaw());
                            if (vcs.size() > i) {
                                event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.misc.screenshare.success.title", "")
                                        .setDescription("http://discordapp.com/channels/" + event.getGuild().getId() + "/" + vcs.get(i).getId() + "/").build()).queue();
                            } else {

                                event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.number.error.title", "").build()).queue();
                                event.getHelpCommand().sendHelp(this, event);
                            }
                        } catch (NumberFormatException e) {
                            event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.id.error.title", "commands.misc.screenshare.number.error.description").build()).queue();
                            event.getHelpCommand().sendHelp(this, event);
                        }
                    }, event);
                } else if (vcs.size()==0) {
                    event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.channel.existing.error", "commands.misc.screenshare.channel.existing.description").build()).queue();
                    event.getHelpCommand().sendHelp(this, event);
                } else {
                    event.getChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.misc.screenshare.success.title", "")
                            .setDescription("http://discordapp.com/channels/" + event.getGuild().getId() + "/" + vcs.get(0).getId() + "/").build()).queue();
                }
            }
        } else {
            event.getHelpCommand().sendHelp(this, event);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"screenshare"};
    }

    @Override
    public String description() {
        return "commands.misc.screenshare.help.description";
    }

    @Override
    public String usage() {
        return "vc-name/id";
    }
}
