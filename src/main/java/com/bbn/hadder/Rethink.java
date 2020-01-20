package com.bbn.hadder;

import com.bbn.hadder.core.Config;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import org.json.JSONArray;

import java.util.NoSuchElementException;

/*
 * @author Skidder / GregTCLTK
 */

public class Rethink {
    private RethinkDB r = RethinkDB.r;
    private Connection conn;
    private Config config;

    Rethink(Config config) {
        this.config = config;
    }

    public void connect() {
        try {
            conn = r.connection()
                    .hostname(config.getDatabaseIP())
                    .db(config.getDatabaseName())
                    .port(config.getDatabasePort())
                    .user(config.getDatabaseUsername(), config.getDatabasePassword())
                    .connect();
            System.out.println("DB CONNECTED");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DB CONNECTION FAILED");
        }
    }

    public void disconnect() {
        conn.close();
        System.out.println("DISCONNECTED");
    }

    private JSONArray getAsArray(String table, String where, String value) {
        try {
            String string = r.table(table).filter(row -> row.g(where.toLowerCase()).eq(value)).coerceTo("array").toJson().run(conn);
            return new JSONArray(string);
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public Object get(String table, String where, String value, String column) {
        JSONArray array = this.getAsArray(table, where, value);
        if (array.length() > 0)
            if (array.getJSONObject(0).has(column))
                return array.getJSONObject(0).get(column);
            else return null;
        else return null;
    }

    public Object getByID(String table, String wherevalue, String column) {
        return r.table(table).get(wherevalue).getField(column).run(conn);
    }

    public void update(String table, String where, String what, String value) {
        try {
            r.table(table).get(where).update(r.hashMap(what, value)).run(conn);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void update(String table, String where, String what, int value) {
        try {
            r.table(table).get(where).update(r.hashMap(what, value)).run(conn);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void update(String table, String where, String what, boolean value) {
        try {
            r.table(table).get(where).update(r.hashMap(what, value)).run(conn);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void insert(String table, Object object) {
        try {
            r.table(table).insert(object).run(conn);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void remove(String table, String where, String value) {
        r.table(table).filter(row -> row.g(where.toLowerCase()).eq(value)).delete().run(conn);
    }

    public void setup() {
        try {
            r.dbCreate("Hadder").run(conn);
        } catch (ReqlOpFailedError e) {
            System.out.println(e.getMessage());
        }
        try {
            r.tableCreate("server").run(conn);
        } catch (ReqlOpFailedError e) {
           System.out.println(e.getMessage());
        }
        try {
            r.tableCreate("user").run(conn);
        } catch (ReqlOpFailedError e) {
            System.out.println(e.getMessage());
        }
        try {
            r.tableCreate("stars").run(conn);
        } catch (ReqlOpFailedError e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUserPrefix(String prefix, String user_id) {
        this.update("user", user_id, "prefix", prefix);
    }

    public String getUserPrefix(String id) {
        return (String) this.getByID("user", id, "prefix");
    }

    public void setGuildPrefix(String prefix, String guild_id) {
        this.update("server", guild_id, "prefix", prefix);
    }

    public String getGuildPrefix(String id) {
        return (String) this.getByID("server", id, "prefix");
    }

    public void insertGuild(String id) {
        this.insert("server", r
                .hashMap("id", id)
                .with("prefix", "h.")
                .with("message_id", "")
                .with("role_id", "")
                .with("invite_detect", false)
                .with("starboard", "")
                .with("neededstars", "4")
        );
    }

    public void insertUser(String id) {
        this.insert("user", r
                .hashMap("id", id)
                .with("prefix", "h.")
                .with("language", "en")
                .with("blacklisted", "none"));
    }

    public void setBlackListed(String id, String commands) {
        this.update("user", id, "blacklisted", commands);
    }

    public String getBlackListed(String id) {
        return (String) this.getByID("user", id, "blacklisted");
    }

    public void setNeededStars(String stars, String guild_id) {
        this.update("server", guild_id, "neededstars", stars);
    }

    public String getNeededStars(String guild_id) {
        return (String) this.getByID("server", guild_id, "neededstars");
    }

    public void setStarboardChannel(String guild_id, String channel_id) {
        this.update("server", guild_id, "starboard", channel_id);
    }

    public String getStarboardChannel(String guild_id) {
        return (String) this.getByID("server", guild_id, "starboard");
    }

    public boolean hasStarboardChannel(String guild_id) {
        return !this.getByID("server", guild_id, "starboard").equals("");
    }

    public void insertStarboardMessage(String message_id, String guild_id, String starboard_message_id) {
        this.insert("stars", r.hashMap("id", message_id).with("guild", guild_id).with("starboardmsg", starboard_message_id));
    }

    public String getStarboardMessage(String message_id) {
        return (String) this.getByID("stars", message_id, "starboardmsg");
    }

    public void removeStarboardMessage(String message_id) {
        this.remove("stars", "id", message_id);
    }

    public boolean hasStarboardMessage(String message_id) {
        return this.getByID("stars", message_id, "guild") != null;
    }

    public void updateRules(String guild_id, String message_id, String role_id, String accept_emote, String decline_emote) {
        this.update("server", guild_id, "message_id", message_id);
        this.update("server", guild_id, "role_id", role_id);
        this.update("server", guild_id, "accept_emote", accept_emote);
        this.update("server", guild_id, "decline_emote", decline_emote);
    }

    public String getRulesMID(String guild_id) {
        return (String) this.getByID("server", guild_id, "message_id");
    }

    public String getRulesRID(String guild_id) {
        return (String) this.getByID("server", guild_id, "role_id");
    }

    public String getRulesAEmote(String guild_id) {
        return (String) this.getByID("server", guild_id, "accept_emote");
    }

    public String getRulesDEmote(String guild_id) {
        return (String) this.getByID("server", guild_id, "decline_emote");
    }

    public void setInviteDetection(String guild_id, boolean b) {
        this.update("server", guild_id, "invite_detect", b);
    }

    public Boolean getInviteDetection(String guild_id) {
        return (Boolean) this.getByID("server", guild_id, "invite_detect");
    }

    public void setLanguage(String user_id, String language) {
        this.update("user", user_id, "language", language);
    }

    public String getLanguage(String user_id) {
        return (String) this.getByID("user", user_id, "language");
    }

}
