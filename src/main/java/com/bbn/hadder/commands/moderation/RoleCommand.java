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
import net.dv8tion.jda.api.entities.Role;

@Perms(Perm.MANAGE_ROLES)
public class RoleCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            if (e.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
                switch (args[0].toLowerCase()) {
                    case "add":
                        if (e.getMessage().getMentionedMembers().size() > 0 && e.getMessage().getMentionedRoles().size() > 0) {
                            for (Member member : e.getMessage().getMentionedMembers()) {
                                for (Role role : e.getMessage().getMentionedRoles()) {
                                    if (e.getGuild().getSelfMember().canInteract(member)) {
                                        if (e.getGuild().getSelfMember().canInteract(role)) {
                                            e.getGuild().addRoleToMember(member, role).reason("Role added by " + e.getAuthor().getAsTag()).queue();
                                        } else {
                                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                        }
                                    } else {
                                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                    }
                                }
                            }
                            e.getChannel().sendMessage(
                                    e.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.role.add.success.title",
                                            "",
                                            "",
                                            "commands.moderation.role.add.success.description",
                                            String.valueOf(e.getMessage().getMentionedRoles().size()),
                                            String.valueOf(e.getMessage().getMentionedMembers().size()))
                                            .build()).queue();
                        }
                        break;

                    case "remove":
                        if (e.getMessage().getMentionedMembers().size() > 0 && e.getMessage().getMentionedRoles().size() > 0) {
                            for (Member member : e.getMessage().getMentionedMembers()) {
                                for (Role role : e.getMessage().getMentionedRoles()) {
                                    if (e.getGuild().getSelfMember().canInteract(member)) {
                                        if (e.getGuild().getSelfMember().canInteract(role)) {
                                            e.getGuild().removeRoleFromMember(member, role).reason("Role removed by " + e.getAuthor().getAsTag()).queue();
                                        } else {
                                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                        }
                                    } else {
                                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
                                    }
                                }
                            }
                            e.getChannel().sendMessage(
                                    e.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.INFO,
                                            "commands.moderation.role.remove.success.title",
                                            "",
                                            "",
                                            "commands.moderation.role.remove.success.description",
                                            String.valueOf(e.getMessage().getMentionedRoles().size()),
                                            String.valueOf(e.getMessage().getMentionedMembers().size()))
                                            .build()).queue();
                        }
                        break;
                    default:
                        e.getHelpCommand().sendHelp(this, e);
                        break;
                }
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"role", "roles"};
    }

    @Override
    public String description() {
        return "commands.moderation.role.help.description";
    }

    @Override
    public String usage() {
        return "[add/remove] [role] [user]";
    }

    @Override
    public String example() {
        return "add @Skidder @Epic-Gamer";
    }
}
