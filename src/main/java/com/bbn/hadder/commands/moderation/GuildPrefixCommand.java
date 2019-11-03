package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class GuildPrefixCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.MANAGE_SERVER) || event.getGuild().getOwnerId().equals(event.getAuthor().getId())) {
            if (args.length == 1) {
                if (!args[0].contains("\"")) {

                    event.getRethink().setServerPrefix(args[0], event.getGuild().getId());

                    EmbedBuilder builder = new EmbedBuilder();

                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("✅ Successfully set ✅").setDescription("I successfully set the new prefix for you to " + args[0]).build()).queue();
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("The prefix must not contain **\"**").build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to set a prefix.").build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"guildprefix"};
    }

    @Override
    public String description() {
        return "Sets the Guild-Prefix";
    }

    @Override
    public String usage() {
        return "<New Guild-Prefix>";
    }
}
