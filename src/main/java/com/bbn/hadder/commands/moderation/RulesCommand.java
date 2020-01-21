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
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@Perms(Perm.MANAGE_SERVER)
public class RulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.moderation.rules.setup.title",
                            "commands.moderation.rules.setup.description")
                            .build()).queue();
            e.getEventWaiter().newOnMessageEventWaiter(e1 -> {
                if (e1.getMessage().getMentionedChannels().size() == 1) {
                    try {
                        TextChannel channel = e1.getMessage().getMentionedChannels().get(0);
                        createRules(e, e1, channel);
                    } catch (Exception ex) {
                        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                "commands.moderation.rules.channel.error.title",
                                "commands.moderation.rules.channel.error.description")
                                .build()).queue();
                    }
                } else {
                    try {
                        TextChannel channel = e1.getGuild().getTextChannelsByName(e1.getMessage().getContentRaw(), true).get(0);
                        createRules(e, e1, channel);
                    } catch (Exception ex) {
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.ERROR,
                                        "commands.moderation.rules.channel.error.title",
                                        "commands.moderation.rules.channel.error.description")
                                        .build()).queue();
                    }
                }
            }, e.getJDA(), e.getAuthor());
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.rules.error.permission.title",
                    "commands.moderation.rules.error.permission.description")
                    .build()).queue();
        }
    }

    public void createRules(CommandEvent e, GuildMessageReceivedEvent e1, TextChannel channel) {
        if (channel.getGuild().getId().equals(e1.getGuild().getId())) {
            if (e.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE)) {
                e1.getChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.rules.rules.title",
                                "",
                                "commands.moderation.rules.rules.description",
                                channel.getName())
                        .build()).queue();
                e.getEventWaiter().newOnMessageEventWaiter(e2 -> {
                    String message = e2.getMessage().getContentRaw();
                    e2.getChannel().sendMessage(
                            e.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.INFO,
                                    "commands.moderation.rules.role.title",
                                    "commands.moderation.rules.role.description")
                            .build()).queue();
                    new EventWaiter().newOnMessageEventWaiter(e3 -> {
                        if (e3.getMessage().getMentionedRoles().size() == 1) {
                            Role role = e3.getMessage().getMentionedRoles().get(0);
                            setRole(e, channel, message, e3, role);
                        } else if (e3.getGuild().getRolesByName(e3.getMessage().getContentRaw(), true).size() > 0) {
                                Role role = e3.getGuild().getRolesByName(e3.getMessage().getContentRaw(), true).get(0);
                                setRole(e, channel, message, e3, role);
                        } else {
                            e3.getChannel().sendMessage(
                                    e.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.ERROR,
                                            "commands.moderation.rules.role.error.title",
                                            "commands.moderation.rules.role.error.description")
                                    .build()).queue();
                        }
                    }, e.getJDA(), e.getAuthor());
                }, e.getJDA(), e.getAuthor());
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.moderation.rules.error.message.title",
                        "commands.moderation.rules.error.message.description")
                        .build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.ERROR,
                            "commands.moderation.rules.guild.error.title",
                            "commands.moderation.rules.guild.error.description")
                    .build()).queue();
        }
    }

    public void setRole(CommandEvent e, TextChannel channel, String message, GuildMessageReceivedEvent e3, Role role) {
        if (e3.getGuild().getSelfMember().canInteract(role)) {
            if (e3.getMember().canInteract(role)) {
                e3.getChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.INFO,
                                "commands.moderation.rules.emote.accept.title",
                                "",
                                "commands.moderation.rules.emote.accept.description", role.getName())
                                .build()).queue();
                e.getEventWaiter().newOnMessageEventWaiter(e4 -> {
                    if (e4.getMessage().getEmotes().size() == 1) {
                        Emote aemote = e4.getMessage().getEmotes().get(0);
                        e4.getChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.rules.emote.decline.title", "",
                                        "commands.moderation.rules.emote.decline.description", String.valueOf(aemote))
                                        .build()).queue();
                        e.getEventWaiter().newOnMessageEventWaiter(e5 -> {
                            Emote demote = e5.getMessage().getEmotes().get(0);
                            if (!aemote.equals(demote)) {
                                Message rules = channel.sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                        .setTitle("Rules")
                                        .setDescription(message)
                                        .build()).complete();
                                try {
                                    rules.addReaction(aemote).queue();
                                    rules.addReaction(demote).queue();
                                    e5.getChannel().sendMessage(
                                            e.getMessageEditor().getMessage(
                                                    MessageEditor.MessageType.INFO,
                                                    "commands.moderation.rules.success.title",
                                                    "",
                                                    "commands.moderation.rules.success.description",
                                                    channel.getAsMention())
                                                    .build()).queue();
                                } catch (Exception ex) {
                                    e5.getChannel().sendMessage(
                                            e.getMessageEditor().getMessage(
                                                    MessageEditor.MessageType.ERROR,
                                                    "error",
                                                    "commands.moderation.rules.emote.error.access.description")
                                                    .build()).queue();
                                    ex.printStackTrace();
                                }
                                e.getRethinkServer().updateRules(rules.getId(), role.getId(), aemote.toString(), demote.toString());
                                e.getRethinkServer().push();
                            } else {
                                e.getTextChannel().sendMessage(
                                        e.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.ERROR,
                                                "commands.moderation.rules.emote.error.equal.title",
                                                "commands.moderation.rules.emote.error.equal.description")
                                                .build()).queue();
                            }
                        }, e.getJDA(), e.getAuthor());
                    } else {
                        String aemote = e4.getMessage().getContentRaw();
                        e4.getChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.rules.emote.decline.title",
                                        "commands.moderation.rules.emoji.decline.description")
                                        .build()).queue();
                        e.getEventWaiter().newOnMessageEventWaiter(e5 -> {
                            String demote = e5.getMessage().getContentRaw();
                            if (!aemote.equals(demote)) {
                                Message rules = channel.sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                        .setTitle("Rules")
                                        .setDescription(message)
                                        .build()).complete();
                                try {
                                    rules.addReaction(aemote).queue();
                                    rules.addReaction(demote).queue();
                                    e5.getChannel().sendMessage(
                                            e.getMessageEditor().getMessage(
                                                    MessageEditor.MessageType.INFO,
                                                    "commands.moderation.rules.success.title",
                                                    "",
                                                    "commands.moderation.rules.success.description",
                                                    channel.getAsMention())
                                                    .build()).queue();
                                } catch (Exception ex) {
                                    e5.getChannel().sendMessage(
                                            e.getMessageEditor().getMessage(
                                                    MessageEditor.MessageType.ERROR,
                                                    "error",
                                                    "commands.moderation.rules.emoji.error.description")
                                                    .build()).queue();
                                    ex.printStackTrace();
                                }
                                e.getRethinkServer().updateRules(rules.getId(), role.getId(), aemote, demote);
                                e.getRethinkServer().push();
                            } else {
                                e.getTextChannel().sendMessage(
                                        e.getMessageEditor().getMessage(
                                                MessageEditor.MessageType.ERROR,
                                                "commands.moderation.rules.emote.error.equal.title",
                                                "commands.moderation.rules.emote.error.equal.description")
                                                .build()).queue();
                            }
                        }, e.getJDA(), e.getAuthor());
                    }
                }, e.getJDA(), e.getAuthor());
            } else {
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.moderation.rules.role.permission.error.title",
                        "commands.moderation.rules.role.permission.error.description")
                        .build()).queue();
            }
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.rules.error.interact.title",
                    "commands.moderation.rules.error.interact.description")
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"rules", "rule", "setup"};
    }

    @Override
    public String description() {
        return "commands.moderation.rules.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
