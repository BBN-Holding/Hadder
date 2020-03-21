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

package com.bbn.hadder.commands.misc;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;
import org.apache.commons.lang3.StringUtils;

@Perms(Perm.VOICE_MOVE_OTHERS)
public class MoveAllCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 2) {
            if (StringUtils.isNumeric(args[0]) && args[0].length() == 18) {
                if (StringUtils.isNumeric(args[1]) && args[1].length() == 18) {
                    int count = e.getGuild().getVoiceChannelById(args[0]).getMembers().size();
                    e.getGuild().getVoiceChannelById(args[0]).getMembers().forEach(
                            member -> e.getGuild().moveVoiceMember(member, e.getGuild().getVoiceChannelById(args[1])).queue()
                    );
                    e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.misc.moveall.success.title", "",
                            "commands.misc.moveall.success.description", String.valueOf(count))
                            .build()).queue();
                } else {
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                            "commands.misc.moveall.error.target.int.title",
                            "commands.misc.moveall.error.target.int.description").build()).queue();
                }
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.misc.moveall.error.source.int.title",
                        "commands.misc.moveall.error.source.int.description").build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"moveall", "move-all", "ma"};
    }

    @Override
    public String description() {
        return "commands.misc.moveall.help.description";
    }

    @Override
    public String usage() {
        return "[source-channel] [target-channel]";
    }

    @Override
    public String example() {
        return "452806287307046923 452858405212782623";
    }
}
