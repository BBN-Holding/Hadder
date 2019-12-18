package com.bbn.hadder;

import com.bbn.hadder.core.Config;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
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
        if (array.length()>0)
            if (array.getJSONObject(0).has(column))
                return array.getJSONObject(0).get(column);
            else return null;
        else return null;
    }

    public String update(String table, String wherevalue, String what, String whatvalue) {
        String out = "";
        try {
            Cursor cursor = r.table(table).get(wherevalue).update(r.hashMap(what, whatvalue)).run(conn);
            out=cursor.toString();
        } catch (ClassCastException ignored) {}
        return out;
    }

    public String insert(String table, Object object) {
        String out = "";
        try {
            Cursor cursor = r.table(table).insert(object).run(conn);
            out = cursor.next().toString();
        } catch (ClassCastException ignored) {}
        return out;
    }

    public void remove(String table, String where, String wherevalue) {
        r.table(table).filter(row -> row.g(where.toLowerCase()).eq(wherevalue)).delete().run(conn);
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

    public void setUserPrefix(String prefix, String userid) {
        this.update("user", userid, "prefix", prefix);
    }

    public String getUserPrefix(String id) {
        return (String) this.get("user", "id", id, "prefix");
    }

    public void setGuildPrefix(String prefix, String guildid) {
        this.update("server", guildid, "prefix", prefix);
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

    public void setNeededstars(String stars, String guildid) {
        this.update("server", guildid, "neededstars", stars);
    }

    public String getNeededstars(String guildid) {
        return (String) this.get("server", "id", guildid, "neededstars");
    }

    public void setStarboardChannel(String guildid, String channelid) {
        this.update("server", guildid, "starboard", channelid);
    }

    public String getStarboardChannel(String guildid) {
        return (String) this.get("server", "id", guildid, "starboard");
    }

    public boolean hasStarboardChannel(String guildid) {
        return !this.get("server", "id", guildid, "starboard").equals("");
    }

    public void insertStarboardMessage(String messageid, String guildid, String starboardmessageid) {
        this.insert("stars", r.hashMap("id", messageid).with("guild", guildid).with("starboardmsg", starboardmessageid));
    }

    public String getStarboardMessage(String messageid) {
        return (String) this.get("stars", "id", messageid, "starboardmsg");
    }

    public void removeStarboardMessage(String messageid) {
        this.remove("stars", "id", messageid);
    }

    public boolean hasStarboardMessage(String messageid) {
        return this.get("stars", "id", messageid, "guild") != null;
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
        this.update("users", user_id, "language", language);
    }

    public String getLanguage(String user_id) {
        return (String) this.get("user", "id", user_id, "language");
    }

}
