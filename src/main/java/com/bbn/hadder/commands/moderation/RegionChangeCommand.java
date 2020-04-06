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
import net.dv8tion.jda.api.Region;

@Perms(Perm.MANAGE_SERVER)
public class RegionChangeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            if (e.getGuild().getSelfMember().hasPermission(Permission.MANAGE_SERVER)) {
                switch (args[0].toLowerCase()) {
                    case "amsterdam":
                        setRegion(Region.AMSTERDAM, "Amsterdam", e);
                        break;
                    case "frankfurt":
                        setRegion(Region.FRANKFURT, "Frankfurt", e);
                        break;
                    case "eu-west":
                        setRegion(Region.EU_WEST, "EU West", e);
                        break;
                    case "eu-central":
                        setRegion(Region.EU_CENTRAL, "EU Central", e);
                        break;
                    case "europe":
                        setRegion(Region.EUROPE, "EUROPE", e);
                        break;
                    case "brazil":
                        setRegion(Region.BRAZIL, "Brazil", e);
                        break;
                    case "hongkong":
                        setRegion(Region.HONG_KONG, "Hong Kong", e);
                        break;
                    case "india":
                        setRegion(Region.INDIA, "India", e);
                        break;
                    case "japan":
                        setRegion(Region.JAPAN, "Japan", e);
                        break;
                    case "london":
                        setRegion(Region.LONDON, "London", e);
                        break;
                    case "russia":
                        setRegion(Region.RUSSIA, "Russia", e);
                        break;
                    case "singapore":
                        setRegion(Region.SINGAPORE, "Singapore", e);
                        break;
                    case "south-africa":
                        setRegion(Region.SOUTH_AFRICA, "South Africa", e);
                        break;
                    case "sydney":
                        setRegion(Region.SYDNEY, "Sydney", e);
                        break;
                    case "us-central":
                        setRegion(Region.US_CENTRAL, "US Central", e);
                        break;
                    case "us-east":
                        setRegion(Region.US_EAST, "US East", e);
                        break;
                    case "us-west":
                        setRegion(Region.US_WEST, "US West", e);
                        break;
                    case "us-south":
                        setRegion(Region.US_SOUTH, "US South", e);
                        break;
                    default:
                        e.getTextChannel().sendMessage(
                                e.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.regionchange.regions.title",
                                        "")
                                        .setDescription("**LOCKED:**\n`amsterdam` `frankfurt` `eu-west` `eu-central` `london`\n\n**UNLOCKED:**\n`europe` `brazil` `hongkong` `india` `japan` `singapore` `south-africa` `sydney` `us-central` `us-east` `us-west` `us-south`")
                                        .build()).queue();
                        break;
                }

            } else
                e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        } else e.getTextChannel().sendMessage(
                e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.moderation.regionchange.regions.title",
                        "")
                        .setDescription("**LOCKED:**\n`amsterdam` `frankfurt` `eu-west` `eu-central` `london`\n\n**UNLOCKED:**\n`europe` `brazil` `hongkong` `india` `japan` `singapore` `south-africa` `sydney` `us-central` `us-east` `us-west` `us-south`")
                        .build()).queue();
    }

    public void setRegion (Region region, String region_name, CommandEvent e) {
        e.getGuild().getManager().setRegion(region).reason("Region changed by " + e.getAuthor().getAsTag()).queue();
        e.getTextChannel().sendMessage(
                e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.moderation.regionchange.success.title", "",
                        "commands.moderation.regionchange.success.description", region_name)
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"changeregion", "cr", "change-region", "region"};
    }

    @Override
    public String description() {
        return "commands.moderation.regionchange.help.description";
    }

    @Override
    public String usage() {
        return "[new region]";
    }

    @Override
    public String example() {
        return "frankfurt";
    }
}
