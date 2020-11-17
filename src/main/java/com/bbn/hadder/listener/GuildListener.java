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

package com.bbn.hadder.listener;

import com.bbn.hadder.core.Config;
import com.bbn.hadder.db.Rethink;
import com.bbn.hadder.utils.BotList;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class GuildListener extends ListenerAdapter {

    private final Rethink rethink;
    private final Config config;

    public GuildListener(Rethink rethink, Config config) {
        this.rethink = rethink;
        this.config = config;
    }

    public void onGuildJoin(GuildJoinEvent e) {
        new Thread(() -> {
            for (Member member : e.getGuild().getMembers()) {
                if (!member.getUser().getId().equals(e.getJDA().getSelfUser().getId())) {
                    rethink.insertUser(member.getUser().getId());
                }
            }
        }).start();

        rethink.insertGuild(e.getGuild().getId());
        e.getJDA().getTextChannelById("759783393230979142").sendMessage(new MessageEditor(null, null).getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Joined Server")
                .setThumbnail(e.getGuild().getIconUrl())
                .addField("Name", e.getGuild().getName(), true)
                .addField("Guild ID", e.getGuild().getId(), true)
                .addField("Guild Owner", e.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("Users", String.valueOf(e.getGuild().getMembers().size()), true)
                .setFooter(e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build()).queue();

        new BotList(config).post();
    }

    public void onGuildLeave(GuildLeaveEvent e) {
        e.getJDA().getTextChannelById("759783393230979142").sendMessage(new MessageEditor(null, null).getMessage(MessageEditor.MessageType.INFO)
                .setTitle("Left Server")
                .setThumbnail(e.getGuild().getIconUrl())
                .addField("Name", e.getGuild().getName(), true)
                .addField("Guild ID", e.getGuild().getId(), true)
                .addField("Guild Owner", e.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("Users", String.valueOf(e.getGuild().getMembers().size()), true)
                .setFooter(e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build()).queue();

        new BotList(config).post();
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if (!e.getUser().getId().equals(e.getJDA().getSelfUser().getId())) {
            rethink.insertUser(e.getUser().getId());
        }
    }
}
