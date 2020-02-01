/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;

public class ProfileCommand implements Command {


    @Override
    public void executed(String[] args, CommandEvent e) {
        Member member = null;
        if (args.length == 0) {
            member = e.getMember();
        } else if (StringUtils.isNumeric(args[0])) {
            member = e.getGuild().getMemberById(args[0]);
        } else if (!StringUtils.isNumeric(args[0]) && args[0].contains("#")) {
            try {
                member = e.getGuild().getMemberByTag(args[0]);
            } catch (IllegalArgumentException ex) {
                // TODO: Translate and give better message
                e.getChannel().sendMessage("Stop it. Just give me some valid user").queue();
            }
        } else if (e.getMessage().getMentionedMembers().size() == 1) {
            member = e.getMessage().getMentionedMembers().get(0);
        }
        try {
            member.getUser();
            // TODO: Translate
            EmbedBuilder embed = e.getMessageEditor()
                    .getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("User Information")
                    .addField("Username", member.getUser().getName(), true)
                    .addField("Usertag", member.getUser().getAsTag(), true)
                    .addField("Userid", member.getUser().getId(), true)
                    .addField("Account Creation Date", member.getUser().getTimeCreated()
                            .format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ").replace("Z", ""), true)
                    .addField("Name on Guild", member.getEffectiveName(), true)
                    .addField("Guild Join Date", member.getTimeJoined()
                            .format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ").replace("Z", ""), true)
                    .addField("Roles", String.valueOf(member.getRoles().size()), true);

            e.getChannel().sendMessage(embed.build()).queue();
        } catch (NullPointerException ex) {
            // TODO: Translate and give better message
            e.getChannel().sendMessage("Stop it. Just give me some valid user").queue();
        }

    }

    @Override
    public String[] labels() {
        return new String[]{"profile", "user"};
    }

    @Override
    public String description() {
        return "Shows some information about the user.";
    }

    @Override
    public String usage() {
        return "@User|UserID|Username";
    }

    @Override
    public String example() {
        return "Hax#6775";
    }
}
