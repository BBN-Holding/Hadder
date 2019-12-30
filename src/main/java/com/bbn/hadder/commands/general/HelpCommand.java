package com.bbn.hadder.commands.general;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            if (args.length == 0) {
                HashMap<String, ArrayList<Command>> hashMap = new HashMap<>();
                for (Command cmd : event.getCommandHandler().getCommandList()) {
                    if (!hashMap.containsKey(cmd.getClass().getPackageName())) {
                        ArrayList<Command> cmdlist = new ArrayList<>();
                        cmdlist.add(cmd);
                        hashMap.put(cmd.getClass().getPackageName(), cmdlist);
                    } else {
                        hashMap.get(cmd.getClass().getPackageName()).add(cmd);
                    }
                }
                EmbedBuilder eb = new EmbedBuilder();
                for (Map.Entry<String, ArrayList<Command>> entry : hashMap.entrySet()) {
                    if (!entry.getKey().endsWith("owner") || (entry.getKey().endsWith("owner") && (event.getAuthor().getId().equals("477141528981012511") ||
                            event.getAuthor().getId().equals("261083609148948488")))) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            Command cmd = entry.getValue().get(i);
                            sb.append("`").append(cmd.labels()[0]).append("`");
                            if (i < entry.getValue().size() - 1) sb.append(", ");
                        }
                        String[] ps = entry.getKey().split("\\.");
                        eb.addField(ps[ps.length - 1], sb.toString(), false);
                    }
                }
                event.getMessageEditor().getMessage(MessageEditor.MessageType.INFO);
                event.getChannel().sendMessage(eb.build()).queue();
            } else {
                for (Command cmd : event.getCommandHandler().getCommandList()) {
                    for (String label : cmd.labels()) {
                        if (label.equalsIgnoreCase(args[0])) {
                            sendHelp(cmd, event);
                        }
                    }
                }
            }
        } else {
            event.getTextChannel().sendMessage(event.getMessageEditor().getTerm("commands.general.help.error.description")).queue();
        }
    }

    public void sendHelp(Command cmd, CommandEvent event) {
        if (!cmd.getClass().getPackageName().endsWith("owner") || (cmd.getClass().getPackageName().endsWith("owner") &&
                (event.getAuthor().getId().equals("477141528981012511") || event.getAuthor().getId().equals("261083609148948488")))) {
            String name = labels()[0];
            event.getChannel().sendMessage(event.getMessageEditor().getMessage(
                    MessageEditor.MessageType.INFO)
                    .setTitle(name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(name.charAt(0)).toUpperCase()))
                    .setDescription(
                            event.getMessageEditor().getTerm("commands.general.help.description") + cmd.description() + "\n" +
                            event.getMessageEditor().getTerm("commands.general.help.usage") + event.getRethink().getGuildPrefix(event.getGuild().getId()) + name + " " + cmd.usage() + "\n" +
                            event.getMessageEditor().getTerm("commands.general.help.example") + event.getRethink().getGuildPrefix(event.getGuild().getId()) + name + "")
                    .build()).queue();

        }
    }

    @Override
    public String[] labels() {
        return new String[]{"help"};
    }

    @Override
    public String description() {
        return "commands.general.help.help.description";
    }

    @Override
    public String usage() {
        return "commands.general.help.help.label";
    }
}
