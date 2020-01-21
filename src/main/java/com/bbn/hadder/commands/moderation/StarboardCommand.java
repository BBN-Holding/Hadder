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
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.TextChannel;

public class StarboardCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getMessage().getMentionedChannels().size()==1) {
            e.getRethink().setStarboardChannel(e.getGuild().getId(), e.getMessage().getMentionedChannels().get(0).getId());
            e.getChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.starboard.success.title","")
                            .build())
                    .queue();
        } else {
            if (args.length>0) {
                TextChannel channel = e.getGuild().getTextChannelById(args[0]);
                if (channel!=null) {
                    e.getRethink().setStarboardChannel(e.getGuild().getId(), channel.getId());
                }
            } else {
                e.getHelpCommand().sendHelp(this, e);
            }
        }

        if (args.length==2) {
            e.getRethink().setNeededStars(args[1], e.getGuild().getId());
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"starboard"};
    }

    @Override
    public String description() {
        return "commands.moderation.starboard.help.description";
    }

    @Override
    public String usage() {
        return "[Channel] [Needed stars]";
    }

    @Override
    public String example() {
        return "#starboard 4";
    }
}
