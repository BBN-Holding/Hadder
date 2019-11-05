package com.bbn.hadder.commands.general;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
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
                if (!entry.getKey().endsWith("owner") || (entry.getKey().endsWith("owner") && (event.getAuthor().getId().equals("477141528981012511") || event.getAuthor().getId().equals("261083609148948488")))) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        Command cmd = entry.getValue().get(i);
                        sb.append("`" + cmd.labels()[0] + "`");
                        if (i < entry.getValue().size() - 1) sb.append(", ");
                    }
                    String[] packagesplit = entry.getKey().split("\\.");
                    eb.addField(packagesplit[packagesplit.length - 1], sb.toString(), false);
                }
            }
            new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, eb);
            event.getChannel().sendMessage(eb.build()).queue();
        } else {
            for (Command cmd : event.getCommandHandler().getCommandList()) {
                for (String label : cmd.labels()) {
                    if (label.toLowerCase().equals(args[0])) {
                        if (!cmd.getClass().getPackageName().endsWith("owner") || (cmd.getClass().getPackageName().endsWith("owner") && (event.getAuthor().getId().equals("477141528981012511") || event.getAuthor().getId().equals("261083609148948488")))) {
                            EmbedBuilder eb = new EmbedBuilder();
                            String name = cmd.labels()[0];
                            eb.setDescription(cmd.description()).setTitle(name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(name.charAt(0)).toUpperCase()));
                            eb.addField("Usage", event.getRethink().getUserPrefix(event.getAuthor().getId()) + cmd.labels()[0] + " " + cmd.usage(), false);
                            new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, eb);
                            event.getChannel().sendMessage(eb.build()).queue();
                        }
                    }
                }
            }
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"help"};
    }

    @Override
    public String description() {
        return "Shows every Command or explains a Command";
    }

    @Override
    public String usage() {
        return "[Commandname]";
    }
}
