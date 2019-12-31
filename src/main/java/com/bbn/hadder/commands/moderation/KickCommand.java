package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Member;

@Perms(Perm.KICK_MEMBERS)
public class KickCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getMessage().getMentionedMembers().size() == 1) {
            Member victim = event.getMessage().getMentionedMembers().get(0);
            if (!event.getAuthor().getId().equals(victim.getId())) {
                if (!event.getJDA().getSelfUser().getId().equals(victim.getId())) {
                    if (event.getGuild().getSelfMember().canInteract(victim)) {
                        event.getGuild().kick(victim, "Kicked by " + event.getAuthor().getAsTag()).queue();
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.kick.success.title",
                                        "✅",
                                        "commands.moderation.kick.success.description",
                                        victim.getUser().getName()).build()).queue();
                    } else {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                    }
                } else {
                    event.getTextChannel().sendMessage(
                            event.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.ERROR,
                                    "commands.moderation.kick.error.title",
                                    "commands.moderation.kick.myself.error.description").build()).queue();
                }
            } else {
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.moderation.kick.error.title", "commands.moderation.kick.yourself.error.description").build()).queue();
            }
        } else if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getHelpCommand().sendHelp(this, event);
        } else if (event.getMessage().getMentionedMembers().size() > 1) {
            for (int i = 0; i < event.getMessage().getMentionedMembers().size(); i++) {
                Member member = event.getMessage().getMentionedMembers().get(i);
                if (!event.getAuthor().getId().equals(member.getId())) {
                    if (!event.getJDA().getSelfUser().getId().equals(member.getId())) {
                        if (event.getGuild().getSelfMember().canInteract(member)) {
                            event.getGuild().kick(member).reason("Mass Kicked by " + event.getAuthor().getAsTag()).queue();
                        } else {
                            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                        }
                    } else {
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.moderation.kick.error.title", "commands.moderation.kick.myself.error.description").build()).queue();
                    }
                } else {
                    event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.moderation.kick.error.title", "commands.moderation.kick.yourself.error.description").build()).queue();
                }
            }
            event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.moderation.kick.success.title", "✅", "commands.moderation.kick.masskick.success.description", String.valueOf(event.getMessage().getMentionedMembers().size())).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"kick"};
    }

    @Override
    public String description() {
        return "commands.moderation.kick.help.description";
    }

    @Override
    public String usage() {
        return "[User(s)]";
    }

    @Override
    public String example() {
        return "@Skidder";
    }
}
