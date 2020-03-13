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
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import com.bbn.hadder.utils.MessageEditor.MessageType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;

public class ScreenShareCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length>0) {
            if (args[0].matches("[0-9]*") && args.length==1 && args[0].length() == 18) {
                if (e.getGuild().getVoiceChannelById(args[0]) != null) {
                    e.getChannel().sendMessage(e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.misc.screenshare.success.title", "")
                            .setDescription("http://discordapp.com/channels/" + e.getGuild().getId() + "/" + args[0] + "/").build()).queue();
                } else {
                    e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.id.error.title", "commands.misc.screenshare.id.error.description").build()).queue();
                    e.getHelpCommand().sendHelp(this, e);
                }
            } else {
                List<VoiceChannel> vcs = e.getGuild().getVoiceChannelsByName(String.join(" ", args), true);
                if (vcs.size() > 1) {
                    EmbedBuilder eb = e.getMessageEditor().getMessage(MessageType.WARNING, "commands.misc.screenshare.channel.error.title", "commands.misc.screenshare.channel.error.description");
                    for (int i = 0; i < vcs.size(); i++) {
                        VoiceChannel voiceChannel = vcs.get(i);
                        eb.addField(i + ": " + voiceChannel.getName(), voiceChannel.getId(), false);
                    }
                    e.getChannel().sendMessage(eb.build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(msge -> {
                        try {
                            int i = Integer.parseInt(msge.getMessage().getContentRaw());
                            if (vcs.size() > i) {
                                e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.misc.screenshare.success.title", "")
                                        .setDescription("http://discordapp.com/channels/" + e.getGuild().getId() + "/" + vcs.get(i).getId() + "/").build()).queue();
                            } else {
                                e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.number.error.title", "").build()).queue();
                                e.getHelpCommand().sendHelp(this, e);
                            }
                        } catch (NumberFormatException ex) {
                            e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.id.error.title", "commands.misc.screenshare.number.error.description").build()).queue();
                            e.getHelpCommand().sendHelp(this, e);
                        }
                    }, e.getJDA(), e.getAuthor());
                } else if (vcs.size()==0) {
                    e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR, "commands.misc.screenshare.channel.existing.error", "commands.misc.screenshare.channel.existing.description").build()).queue();
                    e.getHelpCommand().sendHelp(this, e);
                } else {
                    e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO, "commands.misc.screenshare.success.title", "")
                            .setDescription("http://discordapp.com/channels/" + e.getGuild().getId() + "/" + vcs.get(0).getId() + "/").build()).queue();
                }
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"screenshare"};
    }

    @Override
    public String description() {
        return "commands.misc.screenshare.help.description";
    }

    @Override
    public String usage() {
        return "[channel]";
    }

    @Override
    public String example() {
        return "Talk";
    }
}
