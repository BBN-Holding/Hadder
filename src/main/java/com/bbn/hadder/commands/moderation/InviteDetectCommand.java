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

@Perms(Perm.MANAGE_SERVER)
public class InviteDetectCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            String opinion = args[0].toLowerCase();
            switch (opinion) {
                case "on":
                    if (!event.getRethinkServer().isInvite_detect()) {
                        event.getRethinkServer().setInvite_detect(true);
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.invitedetect.activate.success.title",
                                        "commands.moderation.invitedetect.activate.success.description")
                                        .build()).queue();
                        event.getRethinkServer().push();
                    } else {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "commands.moderation.invitedetect.activate.error.title",
                                "commands.moderation.invitedetect.activate.error.description")
                                .build()).queue();
                    }
                    break;

                case "off":
                    if (event.getRethinkServer().isInvite_detect()) {
                        event.getRethinkServer().setInvite_detect(false);
                        event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.invitedetect.deactivate.success.title",
                                "commands.moderation.invitedetect.deactivate.success.description")
                                .build()).queue();
                        event.getRethinkServer().push();
                    } else {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "commands.moderation.invitedetect.deactivate.error.title",
                                "commands.moderation.invitedetect.deactivate.error.description")
                                .build()).queue();
                    }
                    break;
                default:
                    e.getHelpCommand().sendHelp(this, e);
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"invitedetect", "detectinvite"};
    }

    @Override
    public String description() {
        return "commands.moderation.invitedetect.help.description";
    }

    @Override
    public String usage() {
        return "[on/off]";
    }

    @Override
    public String example() {
        return "on";
    }
}
