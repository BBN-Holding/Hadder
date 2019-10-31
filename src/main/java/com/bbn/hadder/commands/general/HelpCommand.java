package com.bbn.hadder.commands.general;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.core.CommandHandler;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {
    @Override
    public void executed(String[] args, MessageReceivedEvent event) {
        if (args.length==0) {
            HashMap<String, ArrayList<Command>> hashMap = new HashMap<>();
            for (Command cmd : CommandHandler.cmdlist) {
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
                if (!entry.getKey().endsWith("owner")||(entry.getKey().endsWith("owner")&&(event.getAuthor().getId().equals("477141528981012511") || event.getAuthor().getId().equals("261083609148948488")))) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        Command cmd = entry.getValue().get(i);
                        sb.append("`" + cmd.labels()[0] + "`");
                        if (i < entry.getValue().size() - 1) sb.append(", ");
                    }
                    String[] packagesplit = entry.getKey().split(".");
                    eb.addField(packagesplit[packagesplit.length - 1], sb.toString(), false);
                }
            }
            new MessageEditor().setDefaultSettings(MessageEditor.Messagetype.INFO, eb);

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
}
