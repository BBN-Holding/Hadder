package com.bbn.hadder.commands.moderation;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.Region;

public class RegionChangeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (args.length == 1) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_SERVER)) {
                if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
                    switch (args[0].toLowerCase()) {
                        case "amsterdam":
                            event.getGuild().getManager().setRegion(Region.AMSTERDAM).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "frankfurt":
                            event.getGuild().getManager().setRegion(Region.FRANKFURT).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "eu-west":
                            event.getGuild().getManager().setRegion(Region.EU_WEST).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "eu-central":
                            event.getGuild().getManager().setRegion(Region.EU_CENTRAL).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "europe":
                            event.getGuild().getManager().setRegion(Region.EUROPE).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "brazil":
                            event.getGuild().getManager().setRegion(Region.BRAZIL).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "hongkong":
                            event.getGuild().getManager().setRegion(Region.HONG_KONG).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "india":
                            event.getGuild().getManager().setRegion(Region.INDIA).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "japan":
                            event.getGuild().getManager().setRegion(Region.JAPAN).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "london":
                            event.getGuild().getManager().setRegion(Region.LONDON).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "russia":
                            event.getGuild().getManager().setRegion(Region.RUSSIA).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "singapore":
                            event.getGuild().getManager().setRegion(Region.SINGAPORE).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "south_africa":
                            event.getGuild().getManager().setRegion(Region.SOUTH_AFRICA).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "sydney":
                            event.getGuild().getManager().setRegion(Region.SYDNEY).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "us_central":
                            event.getGuild().getManager().setRegion(Region.US_CENTRAL).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "us_east":
                            event.getGuild().getManager().setRegion(Region.US_EAST).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "us-west":
                            event.getGuild().getManager().setRegion(Region.US_WEST).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                        case "us-south":
                            event.getGuild().getManager().setRegion(Region.US_SOUTH).reason("Region changed by " + event.getAuthor().getAsTag()).queue();
                            break;
                    }
                } else event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION).build()).queue();
            } else event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_SELF_PERMISSION).build()).queue();
        } else event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
    }

    @Override
    public String[] labels() {
        return new String[]{"changeregion"};
    }

    @Override
    public String description() {
        return "Changes the server region to locked regions.";
    }

    @Override
    public String usage() {
        return "<region>";
    }
}
