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

package com.bbn.hadder.db;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class RethinkUser {

    private Rethink rethink;

    public String id;
    public String prefix = "h.";
    public String language = "en";
    public String blacklisted = "none";

    public RethinkUser(JSONObject object, Rethink rethink) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.getName().equals("rethink")) {
                try {
                    if (object.has(field.getName()))
                        field.set(this, object.getString(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        this.rethink = rethink;
    }

    public Rethink getRethink() {
        return rethink;
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(String blacklisted) {
        this.blacklisted = blacklisted;
    }

    public void push() {
        rethink.push(this);
    }
}
