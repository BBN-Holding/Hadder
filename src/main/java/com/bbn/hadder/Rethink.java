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
            System.out.println(e.toString());
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

    private Object get(String table, String where, String value, String column) {
        return this.getAsArray(table, where, value).getJSONObject(0).get(column);
    }

    private String update(String table, String wherevalue, String what, String whatvalue) {
        String out="";
        try {
            Cursor cursor = r.table(table).get(wherevalue).update(r.hashMap(what, whatvalue)).run(conn);
            out=cursor.toString();
        } catch (ClassCastException ignored) {}
        return out;
    }

    private String insert(String table, Object object) {
        String out = "";
        try {
            Cursor cursor = r.table(table).insert(object).run(conn);
            out = cursor.next().toString();
        } catch (ClassCastException ignored) {}
        return out;
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

    }

    public String setUserPrefix(String prefix, String userid) {
        return this.update("user", userid, "prefix", prefix);
    }

    public String getUserPrefix(String id) {
        return (String) this.get("user", "id", id, "prefix");
    }

    public String setGuildPrefix(String prefix, String guildid) {
        return this.update("server", guildid, "prefix", prefix);
    }

    public String getGuildPrefix(String id) {
        return (String) this.get("server", "id", id, "prefix");
    }

    public JSONArray getLinks(String id) {
        return new JSONArray((String) this.get("server", "id", id, "links"));
    }

    public String addLinkedGuild(String guildid, String linkid) {
        return this.update("server", guildid, "links", this.getLinks(guildid).put(linkid).toString());
    }

    public String removeLinkedGuild(String guildid, String linkid) {
        JSONArray linkedguildslist = this.getLinks(guildid);
        for (int i = 0; linkedguildslist.length()>i; i++) {
            if (linkedguildslist.getString(i).equals(linkid)) {
                linkedguildslist.remove(i);
                break;
            }
        }
        return this.update("server", guildid, "links", linkedguildslist.toString());
    }

    public String setLinkChannel(String guildid, String channelid) {
        return this.update("server", guildid, "linkchannel", channelid);
    }

    public String getLinkChannel(String guildid) {
        return (String) this.get("server", "id", guildid, "linkchannel");
    }

    public String insertGuild(String id) {
        return this.insert("server", r.hashMap("id", id).with("prefix", "h.").with("links", "[]").with("linkchannel", "").with("message_id", "").with("role_id", ""));
    }

    public String insertUser(String id) {
        return this.insert("user", r.hashMap("id", id).with("prefix", "h."));
    }

    public String updateRules(String guild_id, String message_id, String role_id) {
        this.update("server", guild_id, "message_id", message_id);
        this.update("server", guild_id, "role_id", role_id);
        return null;
    }

    public String getRulesMID(String guild_id) {
        return (String) this.get("server", "id", guild_id, "message_id");
    }

    public String getRulesRID(String guild_id) {
        return (String) this.get("server", "id", guild_id, "role_id");
    }

}
