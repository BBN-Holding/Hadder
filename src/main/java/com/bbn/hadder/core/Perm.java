package com.bbn.hadder.core;

import com.bbn.hadder.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;

/**
 * @author Skidder / GregTCLTK
 * @author Hax / Hax6775 / Schlauer_Hax
 */

public enum Perm {

    BOT_OWNER() {
        @Override
        public boolean check(CommandEvent event) {
            return event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    MANAGE_MESSAGES {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    EMBED_MESSAGES {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.MESSAGE_EMBED_LINKS) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    BAN_MEMBERS {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.BAN_MEMBERS) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    KICK_MEMBERS {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.KICK_MEMBERS) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    MANAGE_SERVER {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    MANAGE_ROLES {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.MANAGE_ROLES) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    MANAGE_NICKNAMES {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.NICKNAME_MANAGE) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    ADMINISTRATOR {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
        },
    MANAGE_WEBHOOKS {
        @Override
        public boolean check(CommandEvent event) {
            return event.getMember().hasPermission(Permission.MANAGE_WEBHOOKS) || event.getConfig().getOwners().contains(event.getAuthor().getIdLong());
        }
    };

    public abstract boolean check(CommandEvent event);
}
