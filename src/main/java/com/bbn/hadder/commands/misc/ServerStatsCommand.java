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
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bbn.hadder.commands.misc;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Date;

public class ServerStatsCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        EmbedBuilder eb = e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                "commands.misc.serverstats.title", "",
                "commands.misc.serverstats.description", e.getGuild().getName())
                .addField("Owner", e.getGuild().getOwner().getUser().getAsTag(), true)
                .addField("ID", e.getGuild().getId(), true)
                .addField("Region", e.getGuild().getRegion().getName(), true)
                .addField("Time created", new Date(e.getGuild().getTimeCreated().toInstant().toEpochMilli()).toString(), true)
                .addField("Roles", String.valueOf(e.getGuild().getRoles().size()), true)
                .addField("Text/Voice/Store Channels", "`" + e.getGuild().getTextChannels().size() + "`" + "/" + "`" + e.getGuild().getVoiceChannels().size() + "`" + "/" + "`" + e.getGuild().getStoreChannels().size() + "`", true)
                .addField("Verification Level", e.getGuild().getVerificationLevel().getKey() + ": " +  e.getGuild().getVerificationLevel(), true)
                .addField("MFA Level", String.valueOf(e.getGuild().getRequiredMFALevel().getKey()), true)
                .setThumbnail(e.getGuild().getIconUrl())
                .setImage(e.getGuild().getBannerUrl());

        if (e.getGuild().getDescription() != null) eb.addField("Description", e.getGuild().getDescription(), true);
        if (e.getGuild().getVanityCode() != null) eb.addField("Vanity Code", "[" + e.getGuild().getVanityCode() + "](https://discord.gg/" + e.getGuild().getVanityCode() + ")", true);

        e.getTextChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"serverstats", "guildstats"};
    }

    @Override
    public String description() {
        return "commands.misc.serverstats.help.description";
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
