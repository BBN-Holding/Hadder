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

package one.bbn.hadder.commands.moderation;

import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.core.Perm;
import one.bbn.hadder.core.Perms;
import one.bbn.hadder.utils.MessageEditor;

@Perms(Perm.MANAGE_SERVER)
public class PrefixCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            if (!args[0].contains("\"")) {
                e.getMongoServer().setPrefix(args[0]);
                e.getMongoServer().push();
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.moderation.prefix.success.title",
                        "",
                        "commands.moderation.prefix.success.description",
                        args[0]).build()
                ).queue();
            } else {
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.WARNING,
                                "",
                                "commands.moderation.prefix.error.description").build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"prefix"};
    }

    @Override
    public String description() {
        return "commands.moderation.prefix.help.description";
    }

    @Override
    public String usage() {
        return "[new prefix]";
    }

    @Override
    public String example() {
        return "!";
    }
}
