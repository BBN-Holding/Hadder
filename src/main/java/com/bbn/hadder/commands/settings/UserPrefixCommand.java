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

package com.bbn.hadder.commands.settings;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class UserPrefixCommand implements Command {

    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            e.getRethink().setUserPrefix(args[0], e.getAuthor().getId());
            e.getTextChannel()
                    .sendMessage(e.getMessageEditor()
                            .getMessage(MessageEditor.MessageType.INFO, "commands.settings.prefix.success.title", "",
                                    "commands.settings.prefix.success.description", args[0])
                            .build())
                    .queue();
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[] { "userprefix" };
    }

    @Override
    public String description() {
        return "commands.settings.prefix.help.description";
    }

    @Override
    public String usage() {
        return "[New Prefix]";
    }

    @Override
    public String example() {
        return "!";
    }
}
