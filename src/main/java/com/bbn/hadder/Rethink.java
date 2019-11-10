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

    private String get(String table, String where, String value, String column) {
        return this.getAsArray(table, where, value).getJSONObject(0).getString(column);
    }

    private String update(String table, String whatvalue, String where, String wherevalue) {
        String out="";
        try {
            Cursor cursor = r.table(table).get(whatvalue).update(r.hashMap(where, wherevalue)).run(conn);
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
        return this.get("user", "id", id, "prefix");
    }

    public String setServerPrefix(String prefix, String guildid) {
        return this.update("server", guildid, "prefix", prefix);
    }

    public String getServerPrefix(String id) {
        return this.get("server", "id", id, "prefix");
    }

    public String insertServer(String id) {
        return this.insert("server", r.hashMap("id", id).with("prefix", "h."));
    }

    public String insertUser(String id) {
        return this.insert("user", r.hashMap("id", id).with("prefix", "h."));
    }

}
