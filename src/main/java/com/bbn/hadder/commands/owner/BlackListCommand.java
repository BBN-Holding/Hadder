/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands.owner;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Perms(Perm.BOT_OWNER)
public class BlackListCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 0) {
            event.getHelpCommand().sendHelp(this, event);
        } else {
            switch (args[0].toLowerCase()) {
                case "add":
                    if (args.length == 3) {
                        Member member = event.getMessage().getMentionedMembers().get(0);
                        String blacklisted = event.getRethink().getBlackListed(member.getId());
                        List<String> commands = new ArrayList<>();
                        if (!blacklisted.equals("none")) commands.addAll(Arrays.asList(blacklisted.split(",")));
                        commands.addAll(Arrays.asList(args[1].split(",")));
                        LinkedHashSet<String> hashSet = new LinkedHashSet<>(commands);

                        ArrayList<String> commandsWithoutDuplicates = new ArrayList<>(hashSet);
                        String newblacklisted = ((commandsWithoutDuplicates.size()!=0) ? String.join(",", commandsWithoutDuplicates) : "none");
                        event.getRethink().setBlackListed(member.getId(), newblacklisted);
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                        .setTitle("Removed Blacklisted Commands from User")
                                        .setDescription("Blacklisted commands: "+newblacklisted)
                                        .build()).queue();
                    }
                    break;
                case "remove":
                    if (args.length == 3) {
                        Member member = event.getMessage().getMentionedMembers().get(0);
                        String blacklisted = event.getRethink().getBlackListed(member.getId());
                        List<String> commands = new ArrayList<>();
                        if (!blacklisted.equals("none")) commands.addAll(Arrays.asList(blacklisted.split(",")));
                        commands.removeAll(Arrays.asList(args[1].split(",")));
                        LinkedHashSet<String> hashSet = new LinkedHashSet<>(commands);

                        ArrayList<String> commandsWithoutDuplicates = new ArrayList<>(hashSet);
                        String newblacklisted = ((commandsWithoutDuplicates.size()!=0) ? String.join(",", commandsWithoutDuplicates) : "none");
                        event.getRethink().setBlackListed(member.getId(), newblacklisted);
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                        .setTitle("Removed Blacklisted Commands from User")
                                        .setDescription("Blacklisted commands: "+newblacklisted)
                                        .build()).queue();
                    }
                    break;
                case "list":
                    StringBuilder stringBuilder = new StringBuilder();
                    for (User user : event.getJDA().getUsers()) {
                        if (!user.getId().equals(event.getJDA().getSelfUser().getId())) {
                            String blacklisted = event.getRethink().getBlackListed(user.getId());
                            if (!blacklisted.equals("none")) {
                                stringBuilder.append(user.getAsTag() + " (" + user.getId() + ") - " + blacklisted + "\n");
                            }
                        }
                    }
                    event.getTextChannel().sendMessage(
                            event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                    .setTitle("Blacklisted Users:")
                                    .setDescription((stringBuilder.length()!=0) ? ("``" + stringBuilder.toString() + "``") : "No blacklisted Users")
                                    .build()).queue();
                    break;
            }
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"blacklist"};
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String usage() {
        return "add|remove|list Commands @Skidder#6775";
    }

    @Override
    public String example() {
        return null;
    }
}
