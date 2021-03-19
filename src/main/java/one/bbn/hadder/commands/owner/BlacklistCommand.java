/*
 * Copyright 2019-2021 GregTCLTK and Schlauer-Hax
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

package one.bbn.hadder.commands.owner;

import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.core.Perm;
import one.bbn.hadder.core.Perms;
import one.bbn.hadder.db.RethinkUser;
import one.bbn.hadder.utils.MessageEditor;
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
                case "remove":
                    if (args.length == 3 && e.getMessage().getMentionedUsers().size() == 1) {
                        RethinkUser u = new RethinkUser(e.getRethink().getObjectByID("user", e.getMessage().getMentionedUsers().get(0).getId()), e.getRethink());
                        String blacklisted = e.getRethinkUser().getBlacklisted();
                        List<String> commands = new ArrayList<>();
                        if (!"none".equals(blacklisted)) commands.addAll(Arrays.asList(blacklisted.split(",")));
                        if (args[0].equalsIgnoreCase("add")) commands.addAll(Arrays.asList(args[1].split(",")));
                        else commands.removeAll(Arrays.asList(args[1].split(",")));
                        LinkedHashSet<String> hashSet = new LinkedHashSet<>(commands);

                        ArrayList<String> commandsWithoutDuplicates = new ArrayList<>(hashSet);
                        String newblacklisted = ((commandsWithoutDuplicates.size() != 0) ? String.join(",", commandsWithoutDuplicates) : "none");
                        u.setBlacklisted(newblacklisted);
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                                        "commands.owner.blacklist.success." + args[0].toLowerCase() + ".title", "",
                                        "commands.owner.blacklist.success." + args[0].toLowerCase() + ".description", newblacklisted)
                                        .build()).queue();
                        u.push();
                    } else e.getHelpCommand().sendHelp(this, e);
                    break;

                case "list":
                    StringBuilder stringBuilder = new StringBuilder();
                    for (User user : e.getJDA().getUsers()) {
                        if (!user.getId().equals(e.getJDA().getSelfUser().getId())) {
                            RethinkUser u = new RethinkUser(e.getRethink().getObjectByID("user", user.getId()), e.getRethink());
                            String blacklisted = u.getBlacklisted();
                            if (!"none".equals(blacklisted)) {
                                stringBuilder.append(user.getAsTag()).append(" (").append(user.getId()).append(") - ").append(blacklisted).append("\n");
                            }
                        }
                    }
                    e.getTextChannel().sendMessage(
                            e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                    .setTitle("Blacklisted Users")
                                    .setDescription((stringBuilder.length() != 0) ? ("``" + stringBuilder.toString() + "``") : "No blacklisted Users")
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
        return "[add|remove|list] [command] [user]";
    }

    @Override
    public String example() {
        return "add solo @Skidder";
    }
}
