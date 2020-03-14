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
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Objects;

public class MoveAllCommand implements Command {

    @Perms(Perm.VOICE_MOVE_OTHERS)
    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 2) {
            int count = Objects.requireNonNull(e.getGuild().getVoiceChannelById(args[0])).getMembers().size();
            Objects.requireNonNull(e.getGuild().getVoiceChannelById(args[0])).getMembers().forEach(
                    member -> {
                        e.getGuild().moveVoiceMember(member, e.getGuild().getVoiceChannelById(args[1])).queue();
                    }
            );
            e.getChannel().sendMessage(new EmbedBuilder().setTitle("Successfully Moved!").setDescription("I moved " +
                     count + " Members. Have fun!").build()).queue();
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
