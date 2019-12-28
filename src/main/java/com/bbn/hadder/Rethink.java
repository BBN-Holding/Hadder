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

    public void update(String table, String value, String what, String whatvalue) {
        try {
            r.table(table).get(value).update(r.hashMap(what, whatvalue)).run(conn);
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
        return (String) this.get("user", "id", id, "prefix");
    }

    public void setGuildPrefix(String prefix, String guild_id) {
        this.update("server", guild_id, "prefix", prefix);
    }

    public String getGuildPrefix(String id) {
        return (String) this.get("server", "id", id, "prefix");
    }

    public void insertGuild(String id) {
        this.insert("server", r.hashMap("id", id)
                .with("prefix", "h.")
                .with("message_id", "")
                .with("role_id", "")
                .with("invite_detect", false)
                .with("starboard", "")
                .with("neededstars", "4")
        );
    }

    public void insertUser(String id) {
        this.insert("user", r.hashMap("id", id).with("prefix", "h.").with("language", "en"));
    }

    public void setNeededstars(String stars, String guild_id) {
        this.update("server", guild_id, "neededstars", stars);
    }

    public String getNeededstars(String guild_id) {
        return (String) this.get("server", "id", guild_id, "neededstars");
    }

    public void setStarboardChannel(String guild_id, String channel_id) {
        this.update("server", guild_id, "starboard", channel_id);
    }

    public String getStarboardChannel(String guild_id) {
        return (String) this.get("server", "id", guild_id, "starboard");
    }

    public boolean hasStarboardChannel(String guild_id) {
        return !this.get("server", "id", guild_id, "starboard").equals("");
    }

    public void insertStarboardMessage(String message_id, String guild_id, String starboardmessageid) {
        this.insert("stars", r.hashMap("id", message_id).with("guild", guild_id).with("starboardmsg", starboardmessageid));
    }

    public String getStarboardMessage(String message_id) {
        return (String) this.get("stars", "id", message_id, "starboardmsg");
    }

    public void removeStarboardMessage(String message_id) {
        this.remove("stars", "id", message_id);
    }

    public boolean hasStarboardMessage(String message_id) {
        return this.get("stars", "id", message_id, "guild") != null;
    }

    public void updateRules(String guild_id, String message_id, String role_id, String accept_emote, String decline_emote) {
        this.update("server", guild_id, "message_id", message_id);
        this.update("server", guild_id, "role_id", role_id);
        this.update("server", guild_id, "accept_emote", accept_emote);
        this.update("server", guild_id, "decline_emote", decline_emote);
    }

    public String getRulesMID(String guild_id) {
        return (String) this.get("server", "id", guild_id, "message_id");
    }

    public String getRulesRID(String guild_id) {
        return (String) this.get("server", "id", guild_id, "role_id");
    }

    public String getRulesAEmote(String guild_id) {
        return this.get("server", "id", guild_id, "accept_emote").toString().replaceAll("\"", "");
    }

    public String getRulesDEmote(String guild_id) {
        return this.get("server", "id", guild_id, "decline_emote").toString().replaceAll("\"", "");
    }

    public void setInviteDetection(String guild_id, boolean b) {
        try {
            r.table("server").get(guild_id).update(r.hashMap("invite_detect", b)).run(conn);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public Boolean getInviteDetection(String guild_id) {
        return (Boolean) this.get("server", "id", guild_id, "invite_detect");
    }

    public void setLanguage(String user_id, String language) {
        this.update("user", user_id, "language", language);
    }

    public String getLanguage(String user_id) {
        return (String) this.get("user", "id", user_id, "language");
    }

}
