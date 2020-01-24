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

package com.bbn.hadder.core;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.RethinkServer;
import com.bbn.hadder.RethinkUser;
import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.commands.general.HelpCommand;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CommandHandler {

    private List<Command> commandList;
    private Config config;
    private HelpCommand helpCommand;

    public CommandHandler(List<Command> commandList, Config config, HelpCommand helpCommand) {
        this.commandList = commandList;
        this.config = config;
        this.helpCommand = helpCommand;
    }

    public void handle(MessageReceivedEvent event, Rethink rethink, String prefix, AudioManager audioManager, RethinkUser rethinkUser, RethinkServer rethinkServer) {
        String invoke = event.getMessage().getContentRaw().replaceFirst(prefix, "").split(" ")[0];
        for (Command cmd : commandList) {
            for (String label : cmd.labels()) {
                if (label.equals(invoke)) {
                    String argString = event.getMessage().getContentRaw()
                            .replaceFirst(prefix, "").replaceFirst(invoke, "");
                    if (argString.startsWith(" ")) argString = argString.replaceFirst(" ", "");
                    String[] args = argString.split(" ");
                    if (args.length > 0 && args[0].equals("")) args = new String[0];

                    CommandEvent commandEvent = new CommandEvent(event.getJDA(), event.getResponseNumber(), event.getMessage(), rethink,
                            config, this, helpCommand, new MessageEditor(rethinkUser, event.getAuthor()), new EventWaiter(), audioManager, rethinkUser, rethinkServer);
                    if (cmd.getClass().getAnnotations().length > 0 && !Arrays.asList(cmd.getClass().getAnnotations()).contains(Perms.class)) {
                        for (Perm perm : cmd.getClass().getAnnotation(Perms.class).value()) {
                            if (!perm.check(commandEvent)) {
                                commandEvent.getTextChannel()
                                        .sendMessage(commandEvent.getMessageEditor().getMessage(MessageEditor.MessageType.NO_PERMISSION)
                                                .setDescription("To execute this command, you need the `" + cmd.getClass().getAnnotation(Perms.class).value()[0] + "` permission.")
                                                .build()).queue();
                                return;
                            }
                        }
                    }

                    boolean run = true;
                    String blacklisted = rethinkUser.getBlacklisted();
                    if (!"none".equals(blacklisted)) {
                        for (String BLLabel : blacklisted.split(",")) {
                            if (Arrays.asList(cmd.labels()).contains(BLLabel)) {
                                run = false;
                            }
                        }
                    }
                    if (run)
                        cmd.executed(args, commandEvent);
                    return;
                }
            }
        }
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
