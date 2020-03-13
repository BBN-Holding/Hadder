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

package com.bbn.hadder.core;

import com.bbn.hadder.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;

public enum Perm {

    BOT_OWNER() {
        @Override
        public boolean check(CommandEvent e) {
            return e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    MANAGE_MESSAGES {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.MESSAGE_MANAGE) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    BAN_MEMBERS {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.BAN_MEMBERS) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    KICK_MEMBERS {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.KICK_MEMBERS) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    MANAGE_SERVER {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.MANAGE_SERVER) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    MANAGE_ROLES {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.MANAGE_ROLES) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    MANAGE_NICKNAMES {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.NICKNAME_MANAGE) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    ADMINISTRATOR {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.ADMINISTRATOR) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    MANAGE_WEBHOOKS {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.MANAGE_WEBHOOKS) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    },
    VOICE_MOVE_OTHERS {
        @Override
        public boolean check(CommandEvent e) {
            return e.getMember().hasPermission(Permission.VOICE_MOVE_OTHERS) || e.getConfig().getOwners().contains(e.getAuthor().getIdLong());
        }
    };

    public abstract boolean check(CommandEvent e);
}
