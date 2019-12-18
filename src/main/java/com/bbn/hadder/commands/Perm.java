/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands;

public enum Perm {

    BOT_OWNER() {
        public boolean check() {
            return true;
        }
    },
    MANAGE_MESSAGES,
    EMBED_MESSAGES,
    BAN_MEMBERS,
    KICK_MEMBERS,
    MANAGE_SERVER,
    MANAGE_ROLES,
    CHANGE_NICKNAME,
    ADMIN_PERMISSIONS

}
