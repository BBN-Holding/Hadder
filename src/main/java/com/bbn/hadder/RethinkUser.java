/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class RethinkUser {

    private Rethink rethink;

    String id;
    String prefix = "h.";
    String language = "en";
    String blacklisted = "none";

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

    public RethinkUser(String id) {
        this.id = id;
    }

    public Rethink getRethink() {
        return rethink;
    }

    public void setRethink(Rethink rethink) {
        this.rethink = rethink;
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
        rethink.pushUser(this);
    }
}