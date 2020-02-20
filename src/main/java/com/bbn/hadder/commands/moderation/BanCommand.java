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
import net.dv8tion.jda.api.entities.Member;

@Perms(Perm.BAN_MEMBERS)
public class BanCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getMessage().getMentionedMembers().size() == 1) {
            Member victim = e.getMessage().getMentionedMembers().get(0);
            if (!e.getAuthor().getId().equals(victim.getId())) {
                if (!e.getJDA().getSelfUser().getId().equals(victim.getId())) {
                    if (e.getGuild().getSelfMember().canInteract(victim)) {
                        e.getGuild().ban(victim, 0, "Banned by " + e.getAuthor().getAsTag()).queue();
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.ban.success.title",
                                        "",
                                        "commands.moderation.ban.success.description",
                                        victim.getUser().getName() + ".").build()).queue();
                    } else {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                    }
                } else {
                    e.getTextChannel().sendMessage(
                            e.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.ERROR,
                                    "commands.moderation.ban.error.title",
                                    "commands.moderation.ban.myself.error.description").build()).queue();
                }
            } else {
                e.getTextChannel().sendMessage
                        (e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "commands.moderation.ban.error.title",
                                "commands.moderation.ban.yourself.error.description").build()).queue();
            }
        } else if (e.getMessage().getMentionedMembers().size() == 0) {
            e.getHelpCommand().sendHelp(this, e);
        } else if (e.getMessage().getMentionedMembers().size() > 1) {
            for (int i = 0; i < e.getMessage().getMentionedMembers().size(); i++) {
                Member member = e.getMessage().getMentionedMembers().get(i);
                if (!e.getAuthor().getId().equals(member.getId())) {
                    if (!e.getJDA().getSelfUser().getId().equals(member.getId())) {
                        if (e.getGuild().getSelfMember().canInteract(member)) {
                            e.getGuild().ban(member, 0).reason("Mass Ban by " + e.getAuthor().getAsTag()).queue();
                        } else {
                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                        }
                    } else {
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.ERROR,
                                        "commands.moderation.ban.error.title",
                                        "commands.moderation.ban.myself.error.description").build()).queue();
                    }
                } else {
                    e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.ERROR,
                            "commands.moderation.ban.error.title",
                            "commands.moderation.ban.yourself.error.description").build()).queue();
                }
            }
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.moderation.ban.success.title",
                    "",
                    "commands.moderation.ban.massban.success.description",
                    String.valueOf(e.getMessage().getMentionedMembers().size())).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"ban"};
    }

    @Override
    public String description() {
        return "commands.moderation.ban.help.description";
    }

    @Override
    public String usage() {
        return "[user]";
    }

    @Override
    public String example() {
        return "@Skidder";
    }
}

