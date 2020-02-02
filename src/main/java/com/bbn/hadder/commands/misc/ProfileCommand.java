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
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;

public class ProfileCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        User u = null;
        if (args.length == 0) {
            u = e.getAuthor();
        } else if (StringUtils.isNumeric(args[0]) && args[0].length() == 18) {
            u = e.getJDA().getUserById(args[0]);
        } else if (!StringUtils.isNumeric(args[0]) && args[0].contains("#")) {
            try {
                u = e.getJDA().getUserByTag(args[0]);
            } catch (Exception ex) {
                e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                        "commands.misc.profile.error.title",
                        "commands.misc.profile.error.description").build()).queue();
                return;
            }
        } else if (e.getMessage().getMentionedUsers().size() == 1) {
            u = e.getMessage().getMentionedUsers().get(0);
        }
        try {
            // TODO: Translate
            EmbedBuilder embed = e.getMessageEditor()
                    .getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("User Information")
                    .addField("Username", u.getName(), true)
                    .addField("Tag", u.getAsTag(), true)
                    .addField("ID", u.getId(), true)
                    .addField("Account Creation Date", u.getTimeCreated()
                            .format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ").replace("Z", ""), true)
                    .addField("Nickname", e.getGuild().getMember(u).getEffectiveName(), true)
                    .addField("Guild Join Date", e.getGuild().getMember(u).getTimeJoined()
                            .format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ").replace("Z", ""), true)
                    .addField("Roles", String.valueOf(e.getGuild().getMember(u).getRoles().size()), true);

            e.getChannel().sendMessage(embed.build()).queue();
        } catch (NullPointerException ex) {
            e.getChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.misc.profile.error.title",
                    "commands.misc.profile.error.description").build()).queue();
        }

    }

    @Override
    public String[] labels() {
        return new String[]{"profile", "user", "userinfo"};
    }

    @Override
    public String description() {
        return "commands.misc.profile.help.description";
    }

    @Override
    public String usage() {
        return "[user/id]";
    }

    @Override
    public String example() {
        return "Hax#6775";
    }
}
