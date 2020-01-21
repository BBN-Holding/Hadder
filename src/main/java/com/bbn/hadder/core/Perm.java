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
