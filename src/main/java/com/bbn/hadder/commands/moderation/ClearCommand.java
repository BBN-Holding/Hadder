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

package com.bbn.hadder.commands.moderation;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Perms(Perm.MANAGE_MESSAGES)
public class ClearCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                if (args[0].equals("all")) {
                    List<Message> msg = e.getTextChannel().getIterableHistory().complete();
                    for (Message message : msg) {
                        message.delete().queue();
                    }
                    Message message = e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.moderation.clear.all.success.title",
                            "",
                            "commands.moderation.clear.all.success.description",
                            String.valueOf(msg.size()))
                            .build()).complete();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    message.delete().queue();
                } else {
                    try {
                        int nbToDelete = Integer.parseInt(args[0]);
                        if (nbToDelete < 1 || nbToDelete > 99) {
                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.moderation.clear.number.error.title", "commands.moderation.clear.number.error.description").build()).queue();
                        } else {
                            List<Message> history = e.getTextChannel().getHistory().retrievePast(nbToDelete + 1).complete();
                            List<Message> msgToDelete = new ArrayList<>(history);
                            e.getTextChannel().deleteMessages(msgToDelete).queue();
                            if (nbToDelete == 1) {
                                Message msg = e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.moderation.clear.success.title",
                                        "commands.moderation.clear.success.description.singular").build()).complete();
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                msg.delete().queue();
                            } else {
                                Message msg = e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.moderation.clear.success.title", "",
                                        "commands.moderation.clear.success.description.plural", String.valueOf(nbToDelete)).build()).complete();
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                msg.delete().queue();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        e.getHelpCommand().sendHelp(this, e);
                    } catch (IllegalArgumentException ex) {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.moderation.clear.message.error.title", "commands.moderation.clear.message.error.description")
                                .build()).queue();
                    }
                }
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"clear"};
    }

    @Override
    public String description() {
        return "commands.moderation.clear.help.description";
    }

    @Override
    public String usage() {
        return "[amount]";
    }

    @Override
    public String example() {
        return "69";
    }
}
