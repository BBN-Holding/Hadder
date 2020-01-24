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

package com.bbn.hadder.commands.owner;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Perms(Perm.BOT_OWNER)
public class BlacklistCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 0) {
            e.getHelpCommand().sendHelp(this, e);
        } else {
            switch (args[0].toLowerCase()) {
                case "add":
                    if (args.length == 3) {
                        String blacklisted = e.getRethinkUser().getBlacklisted();
                        List<String> commands = new ArrayList<>();
                        if (!"none".equals(blacklisted)) commands.addAll(Arrays.asList(blacklisted.split(",")));
                        commands.addAll(Arrays.asList(args[1].split(",")));
                        LinkedHashSet<String> hashSet = new LinkedHashSet<>(commands);

                        ArrayList<String> commandsWithoutDuplicates = new ArrayList<>(hashSet);
                        String newblacklisted = ((commandsWithoutDuplicates.size()!=0) ? String.join(",", commandsWithoutDuplicates) : "none");
                        e.getRethinkUser().setBlacklisted(newblacklisted);
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                        .setTitle("Removed Blacklisted Commands from User")
                                        .setDescription("Blacklisted commands: "+newblacklisted)
                                        .build()).queue();
                        e.getRethinkUser().push();
                    }
                    break;

                case "remove":
                    if (args.length == 3) {
                        String blacklisted = e.getRethinkUser().getBlacklisted();
                        List<String> commands = new ArrayList<>();
                        if (!"none".equals(blacklisted)) commands.addAll(Arrays.asList(blacklisted.split(",")));
                        commands.removeAll(Arrays.asList(args[1].split(",")));
                        LinkedHashSet<String> hashSet = new LinkedHashSet<>(commands);

                        ArrayList<String> commandsWithoutDuplicates = new ArrayList<>(hashSet);
                        String newblacklisted = ((commandsWithoutDuplicates.size()!=0) ? String.join(",", commandsWithoutDuplicates) : "none");
                        e.getRethinkUser().setBlacklisted(newblacklisted);
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                        .setTitle("Removed Blacklisted Commands from User")
                                        .setDescription("Blacklisted commands: "+newblacklisted)
                                        .build()).queue();
                        e.getRethinkUser().push();
                    }
                    break;

                case "list":
                    StringBuilder stringBuilder = new StringBuilder();
                    for (User user : e.getJDA().getUsers()) {
                        if (!user.getId().equals(e.getJDA().getSelfUser().getId())) {
                            String blacklisted = e.getRethinkUser().getBlacklisted();
                            if (!"none".equals(blacklisted)) {
                                stringBuilder.append(user.getAsTag()).append(" (").append(user.getId()).append(") - ").append(blacklisted).append("\n");
                            }
                        }
                    }
                    e.getTextChannel().sendMessage(
                            e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                    .setTitle("Blacklisted Users:")
                                    .setDescription((stringBuilder.length()!=0) ? ("``" + stringBuilder.toString() + "``") : "No blacklisted Users")
                                    .build()).queue();
                    break;

                default:
                    e.getHelpCommand().sendHelp(this, e);
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
        return "commands.owner.blacklist.help.description";
    }

    @Override
    public String usage() {
        return "add|remove|list command @User";
    }

    @Override
    public String example() {
        return "add solo @Skidder";
    }
}
