package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

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
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                switch (args[0].toLowerCase()) {
                    case "amsterdam":
                        setRegion(Region.AMSTERDAM, "Amsterdam", event);
                        break;
                    case "frankfurt":
                        setRegion(Region.FRANKFURT, "Frankfurt", event);
                        break;
                    case "eu-west":
                        setRegion(Region.EU_WEST, "EU West", event);
                        break;
                    case "eu-central":
                        setRegion(Region.EU_CENTRAL, "EU Central", event);
                        break;
                    case "europe":
                        setRegion(Region.EUROPE, "EUROPE", event);
                        break;
                    case "brazil":
                        setRegion(Region.BRAZIL, "Brazil", event);
                        break;
                    case "hongkong":
                        setRegion(Region.HONG_KONG, "Hong Kong", event);
                        break;
                    case "india":
                        setRegion(Region.INDIA, "India", event);
                        break;
                    case "japan":
                        setRegion(Region.JAPAN, "Japan", event);
                        break;
                    case "london":
                        setRegion(Region.LONDON, "London", event);
                        break;
                    case "russia":
                        setRegion(Region.RUSSIA, "Russia", event);
                        break;
                    case "singapore":
                        setRegion(Region.SINGAPORE, "Singapore", event);
                        break;
                    case "south-africa":
                        setRegion(Region.SOUTH_AFRICA, "South Africa", event);
                        break;
                    case "sydney":
                        setRegion(Region.SYDNEY, "Sydney", event);
                        break;
                    case "us-central":
                        setRegion(Region.US_CENTRAL, "US Central", event);
                        break;
                    case "us-east":
                        setRegion(Region.US_EAST, "US East", event);
                        break;
                    case "us-west":
                        setRegion(Region.US_WEST, "US West", event);
                        break;
                    case "us-south":
                        setRegion(Region.US_SOUTH, "US South", event);
                        break;
                    case "list":
                        event.getTextChannel().sendMessage(
                                event.getMessageEditor().getMessage(
                                        MessageEditor.MessageType.INFO,
                                        "commands.moderation.regionchange.regions.title",
                                        "")
                                        .setDescription("**LOCKED:**\n`amsterdam` `frankfurt` `eu-west` `eu-central` `london`\n\n**UNLOCKED:**\n`europe` `brazil` `hongkong` `india` `japan` `singapore` `south-africa` `sydney` `us-central` `us-east` `us-west` `us-south`")
                                        .build()).queue();
                        break;
                }
            } else
                event.getTextChannel().sendMessage(event.getMessageEditor().getMessage(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        } else event.getTextChannel().sendMessage(
                event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.moderation.regionchange.regions.title",
                        "")
                        .setDescription("**LOCKED:**\n`amsterdam` `frankfurt` `eu-west` `eu-central` `london`\n\n**UNLOCKED:**\n`europe` `brazil` `hongkong` `india` `japan` `singapore` `south-africa` `sydney` `us-central` `us-east` `us-west` `us-south`")
                        .build()).queue();
    }

    public void setRegion (Region region, String region_name, CommandEvent event) {
        event.getGuild().getManager().setRegion(region).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
        event.getTextChannel().sendMessage(
                event.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.moderation.regionchange.success.title", "",
                        "commands.moderation.regionchange.success.description", region_name)
                .build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"changeregion", "cr", "change-region"};
    }

    @Override
    public String description() {
        return "commands.moderation.regionchange.help.description";
    }

    @Override
    public String usage() {
        return "region";
    }
}
