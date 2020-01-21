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
import net.dv8tion.jda.api.entities.TextChannel;

@Perms(Perm.MANAGE_SERVER)
public class EditRulesCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getRethink().getRulesMID(e.getGuild().getId()).length() == 18) {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.moderation.editrules.message.title",
                    "commands.moderation.editrules.message.description").build()).queue();

            e.getEventWaiter().newOnMessageEventWaiter(e1 -> {
                String rules = e1.getMessage().getContentRaw();
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                        "commands.moderation.editrules.channel.title",
                        "commands.moderation.editrules.channel.description").build()).queue();

                e.getEventWaiter().newOnMessageEventWaiter(e2 -> {
                    if (e2.getMessage().getMentionedChannels().size() == 1) {
                        try {
                            TextChannel channel = e2.getMessage().getMentionedChannels().get(0);
                            checkChannel(e, rules, channel);
                        } catch (Exception ex) {
                            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                                    "commands.moderation.editrules.channel.error.title",
                                    "commands.moderation.editrules.channel.error.description")
                                    .build()).queue();
                        }
                    } else {
                        try {
                            TextChannel channel = e1.getGuild().getTextChannelsByName(e2.getMessage().getContentRaw(), true).get(0);
                            checkChannel(e, rules, channel);
                        } catch (Exception ex) {
                            e.getTextChannel().sendMessage(
                                    e.getMessageEditor().getMessage(
                                            MessageEditor.MessageType.ERROR,
                                            "commands.moderation.editrules.channel.error.title",
                                            "commands.moderation.editrules.channel.error.description")
                                            .build()).queue();
                        }
                    }
                }, e.getJDA(), e.getAuthor());
            }, e.getJDA(), e.getAuthor());
        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.editrules.error.title", "",
                    "commands.moderation.editrules.error.description", e.getRethink().getGuildPrefix(e.getGuild().getId())).build()).queue();
        }
    }

    public void checkChannel(CommandEvent e, String rules, TextChannel channel) {
        try {
            channel.retrieveMessageById(e.getRethink().getRulesMID(e.getGuild().getId())).queue();
            setRules(e, rules, channel);
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.moderation.editrules.success.title",
                    "commands.moderation.editrules.success.description").build()).queue();
        } catch (Exception ex) {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.moderation.editrules.channel.message.error.title",
                    "commands.moderation.editrules.channel.message.error.description").build()).queue();
        }
    }

    public void setRules(CommandEvent e, String rules, TextChannel channel) {
        channel.retrieveMessageById(e.getRethink().getRulesMID(e.getGuild().getId())).complete().editMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Rules")
                .setDescription(rules)
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"editrules", "rulesedit", "edit_rules", "rules_edit"};
    }

    @Override
    public String description() {
        return "commands.moderation.editrules.help.description";
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
