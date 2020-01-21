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
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@Perms(Perm.MANAGE_NICKNAMES)
public class NickCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getGuild().getSelfMember().hasPermission(Permission.NICKNAME_MANAGE)) {
            if (e.getMessage().getMentionedMembers().size() == 1) {
                if (!e.getMessage().getMentionedMembers().get(0).getId().equals(e.getGuild().getSelfMember().getId())) {
                    if (e.getGuild().getSelfMember().canInteract(e.getMessage().getMentionedMembers().get(0))) {
                        if (args.length > 1) {
                            if (e.getMessage().getContentRaw().startsWith(e.getRethinkUser().getPrefix())) {
                                e.getGuild().modifyNickname(e.getMessage().getMentionedMembers().get(0), e.getMessage().getContentRaw().replaceFirst(e.getRethinkUser().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + e.getAuthor().getAsTag()).queue();
                                e.getTextChannel().sendMessage(
                                        e.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.INFO,
                                                "commands.moderation.nick.success.title",
                                                "✅",
                                                "commands.moderation.nick.success.description",
                                                e.getMessage().getMentionedMembers().get(0).getUser().getAsTag()
                                        ).build()).queue();
                            } else if (e.getMessage().getContentRaw().startsWith(e.getRethinkServer().getPrefix())) {
                                e.getGuild().modifyNickname(e.getMessage().getMentionedMembers().get(0), e.getMessage().getContentRaw().replaceFirst(e.getRethinkServer().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + e.getAuthor().getAsTag()).queue();
                                e.getTextChannel().sendMessage(
                                        e.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.INFO,
                                                "commands.moderation.nick.success.title",
                                                "✅",
                                                "commands.moderation.nick.success.description", e.getMessage().getMentionedMembers().get(0).getUser().getAsTag()
                                        ).build()).queue();
                            }
                        } else {
                            e.getHelpCommand().sendHelp(this, e);
                        }
                    } else {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                    }
                } else {
                    if (e.getMessage().getContentRaw().startsWith(e.getRethinkUser().getPrefix())) {
                        e.getGuild().getSelfMember().modifyNickname(e.getMessage().getContentRaw().replaceFirst(e.getRethinkUser().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + e.getAuthor().getAsTag()).queue();
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.nick.success.title",
                                        "✅",
                                        "commands.moderation.nick.myself.success.description",
                                        "").build()).queue();
                    } else if (e.getMessage().getContentRaw().startsWith(e.getRethinkServer().getPrefix())) {
                        e.getGuild().getSelfMember().modifyNickname(e.getMessage().getContentRaw().replaceFirst(e.getRethinkServer().getPrefix() + "nick " + args[0], "")).reason("Nicked by " + e.getAuthor().getAsTag()).queue();
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.nick.success.title",
                                        "✅",
                                        "commands.moderation.nick.myself.success.description", ""
                                ).build()).queue();
                    }
                }
            } else if (e.getMessage().getMentionedMembers().size() == 0) {
                e.getHelpCommand().sendHelp(this, e);
            } else {
                for (int i = 0; i < e.getMessage().getMentionedMembers().size(); i++) {
                    Member member = e.getMessage().getMentionedMembers().get(i);
                    if (!e.getJDA().getSelfUser().getId().equals(member.getId())) {
                        e.getGuild().modifyNickname(member, args[args.length - 1]).reason("Mass Nicked by " + e.getAuthor().getAsTag()).queue();
                    } else {
                        e.getGuild().getSelfMember().modifyNickname(args[args.length - 1]).reason("Mass Nicked by " + e.getAuthor().getAsTag()).queue();
                    }
                }
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.nick.success.title",
                                "✅",
                                "commands.moderation.nick.myself.success.description", String.valueOf(e.getMessage().getMentionedMembers().size())
                        ).build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"nick"};
    }

    @Override
    public String description() {
        return "commands.moderation.nick.help.description";
    }

    @Override
    public String usage() {
        return "[User(s)] [New nickname]";
    }

    @Override
    public String example() {
        return "@Skidder Cutie";
    }
}
