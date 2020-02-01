/*
 * Copyright 2019-2020 GregTCLTK and Schlauer-Hax
 *
 * Licensed under the GNU Affero General Public License, Version 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.gnu.org/licenses/agpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bbn.hadder.commands.general;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelpCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            if (args.length == 0) {
                HashMap<String, ArrayList<Command>> hashMap = new HashMap<>();
                for (Command cmd : e.getCommandHandler().getCommandList()) {
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
                    if (!entry.getKey().endsWith("owner") || (entry.getKey().endsWith("owner") && (e.getAuthor().getId().equals("477141528981012511") ||
                            e.getAuthor().getId().equals("261083609148948488")))) {
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
                e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO);
                e.getChannel().sendMessage(eb.build()).queue();
            } else {
                for (Command cmd : e.getCommandHandler().getCommandList()) {
                    for (String label : cmd.labels()) {
                        if (label.equalsIgnoreCase(args[0])) {
                            sendHelp(cmd, e);
                        }
                    }
                }
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getTerm("commands.general.help.error.description")).queue();
        }
    }

    public void sendHelp(Command cmd, CommandEvent e) {
        if (!cmd.getClass().getPackageName().endsWith("owner") || (cmd.getClass().getPackageName().endsWith("owner") &&
                (e.getAuthor().getId().equals("477141528981012511") || e.getAuthor().getId().equals("261083609148948488")))) {
            String name = cmd.labels()[0];
            StringBuilder b = new StringBuilder();
            b.append(e.getMessageEditor().getTerm("commands.general.help.description")).append(" ").append(e.getMessageEditor().getTerm(cmd.description())).append("\n");
            if (cmd.usage() != null) {
                b.append(e.getMessageEditor().getTerm("commands.general.help.usage")).append(" ").append(e.getRethinkServer().getPrefix()).append(name).append(" ").append(cmd.usage()).append("\n");
            }
            if (cmd.example() != null) {
                b.append(e.getMessageEditor().getTerm("commands.general.help.example")).append(" ").append(e.getRethinkServer().getPrefix()).append(name).append(" ").append(cmd.example());
            }
            e.getChannel().sendMessage(e.getMessageEditor().getMessage(
                    MessageEditor.MessageType.INFO)
                    .setTitle(cmd.labels()[0])
                    .setDescription(b.toString())
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
        return "[command]";
    }

    @Override
    public String example() {
        return "ban";
    }
}
