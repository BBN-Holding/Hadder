package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

public class NickCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMember().hasPermission(Permission.NICKNAME_MANAGE) || event.getGuild().getOwnerId().equals(event.getMember().getId())) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.NICKNAME_MANAGE)) {
                if (event.getMessage().getMentionedMembers().size() == 1) {
                    if (event.getGuild().getSelfMember().canInteract(event.getMessage().getMentionedMembers().get(0))) {
                        if (args.length > 1) {
                            if (event.getMessage().getContentRaw().startsWith(event.getRethink().getUserPrefix(event.getMember().getId()))) {
                                event.getGuild().modifyNickname(event.getMessage().getMentionedMembers().get(0), event.getMessage().getContentRaw().replaceFirst(event.getRethink().getUserPrefix(event.getMember().getId()) + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("✅ Successfully nicked ✅").setDescription("I successfully nicked " + event.getMessage().getMentionedMembers().get(0).getUser().getAsTag() + ".").build()).queue();
                            } else if (event.getMessage().getContentRaw().startsWith(event.getRethink().getServerPrefix(event.getGuild().getId()))) {
                                event.getGuild().modifyNickname(event.getMessage().getMentionedMembers().get(0), event.getMessage().getContentRaw().replaceFirst(event.getRethink().getServerPrefix(event.getGuild().getId()) + "nick " + args[0], "")).reason("Nicked by " + event.getAuthor().getAsTag()).queue();
                                EmbedBuilder builder = new EmbedBuilder();
                                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, builder).setTitle("✅ Successfully nicked ✅").setDescription("I successfully nicked " + event.getMessage().getMentionedMembers().get(0).getUser().getAsTag() + ".").build()).queue();
                            }
                        } else {
                            EmbedBuilder builder = new EmbedBuilder();
                            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setTitle("Missing arguments").setDescription("You have to specify a new nickname for the user(s).").build()).queue();
                        }
                    } else {
                        EmbedBuilder builder = new EmbedBuilder();
                        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_SELF_PERMISSION, builder).build()).queue();
                    }
                } else if (event.getMessage().getMentionedMembers().size() == 0) {
                    EmbedBuilder builder = new EmbedBuilder();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.WARNING, builder).setDescription("You have to mention at least one user!").build()).queue();
                } else {
                    event.getTextChannel().sendMessage("Mass Nicks == soon").queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_SELF_PERMISSION, builder).build()).queue();
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"nick"};
    }

    @Override
    public String description() {
        return "Rename a user";
    }

    @Override
    public String usage() {
        return "<@user> <New Nickname>";
    }
}
