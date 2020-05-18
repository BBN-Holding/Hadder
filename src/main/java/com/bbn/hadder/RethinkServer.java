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

package com.bbn.hadder;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class RethinkServer {

    private Rethink rethink;

    public String accept_emote = "";
    public String decline_emote = "";
    public String id;
    public boolean invite_detect = false;
    public String message_id = "";
    public String neededstars = "3";
    public String prefix = "h.";
    public String role_id = "";
    public String starboard = "";

    public RethinkServer(JSONObject object, Rethink rethink) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.getName().equals("rethink")) {
                try {
                    if (object.has(field.getName()))
                        field.set(this, object.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        this.rethink = rethink;
    }

    public String getAcceptEmote() {
        return accept_emote;
    }

    public void setAcceptEmote(String accept_emote) {
        this.accept_emote = accept_emote;
    }

    public String getDeclineEmote() {
        return decline_emote;
    }

    public void setDeclineEmote(String decline_emote) {
        this.decline_emote = decline_emote;
    }

    public String getId() {
        return id;
    }

    public boolean hasInviteDetect() {
        return invite_detect;
    }

    public void setInviteDetect(boolean invite_detect) {
        this.invite_detect = invite_detect;
    }

    public String getMessageID() {
        return message_id;
    }

    public void setMessageID(String message_id) {
        this.message_id = message_id;
    }

    public String getNeededStars() {
        return neededstars;
    }

    public void setNeededStars(String neededstars) {
        this.neededstars = neededstars;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getRoleID() {
        return role_id;
    }

    public void setRoleID(String role_id) {
        this.role_id = role_id;
    }

    public String getStarboard() {
        return starboard;
    }

    public void setStarboard(String starboard) {
        this.starboard = starboard;
    }

    public boolean hasStarboardChannel() {
        return !"".equals(starboard);
    }

    public void updateRules(String message_id, String role_id, String accept_emote, String decline_emote) {
        this.setMessageID(message_id);
        this.setRoleID(role_id);
        this.setAcceptEmote(accept_emote);
        this.setDeclineEmote(decline_emote);
    }

    public void push() {
        rethink.push(this);
    }
}
