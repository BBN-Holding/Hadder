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
import net.dv8tion.jda.api.entities.Guild;

@Perms(Perm.BOT_OWNER)
public class GuildLeaveCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            Guild guild = e.getJDA().getGuildById(args[0]);
            try {
                guild.leave().queue();
                e.getTextChannel()
                        .sendMessage(e.getMessageEditor()
                                .getMessage(MessageEditor.MessageType.INFO, "commands.owner.guildleave.success.title",
                                        "", "commands.owner.guildleave.success.description", guild.getName())
                                .build())
                        .queue();
            } catch (Exception ex) {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.owner.guildleave.error.title", "", "commands.owner.guildleave.help.description", guild.getName()).build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"guildleave"};
    }

    @Override
    public String description() {
        return "commands.owner.guildleave.help.description";
    }

    @Override
    public String usage() {
        return "[id]";
    }

    @Override
    public String example() {
        return "366971954244354048";
    }
}
