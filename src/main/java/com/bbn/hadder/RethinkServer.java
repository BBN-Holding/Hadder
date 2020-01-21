/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class RethinkServer {

    private Rethink rethink;

    String accept_emote = "";
    String decline_emote = "";
    String id;
    boolean invite_detect = false;
    String message_id = "";
    String neededstars = "3";
    String prefix = "h.";
    String role_id = "";
    String starboard = "";

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

    public String getAccept_emote() {
        return accept_emote;
    }

    public void setAccept_emote(String accept_emote) {
        this.accept_emote = accept_emote;
    }

    public String getDecline_emote() {
        return decline_emote;
    }

    public void setDecline_emote(String decline_emote) {
        this.decline_emote = decline_emote;
    }

    public String getId() {
        return id;
    }

    public boolean isInvite_detect() {
        return invite_detect;
    }

    public void setInvite_detect(boolean invite_detect) {
        this.invite_detect = invite_detect;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getNeededstars() {
        return neededstars;
    }

    public void setNeededstars(String neededstars) {
        this.neededstars = neededstars;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getStarboard() {
        return starboard;
    }

    public void setStarboard(String starboard) {
        this.starboard = starboard;
    }

    public void updateRules(String message_id, String role_id, String accept_emote, String decline_emote) {
        this.setMessage_id(message_id);
        this.setRole_id(role_id);
        this.setAccept_emote(accept_emote);
        this.setDecline_emote(decline_emote);
    }

    public void push() {
        rethink.pushServer(this);
    }
}
