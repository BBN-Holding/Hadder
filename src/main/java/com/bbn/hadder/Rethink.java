package com.bbn.hadder;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import com.google.gson.JsonParser;

import java.util.NoSuchElementException;

/*
 * @author Skidder / GregTCLTK
 */

public class Rethink {
    private static RethinkDB r = RethinkDB.r;
    static Connection conn;

    public static boolean connect() {
        try {
            conn = r.connection().hostname("127.0.0.1").db("Hadder").port(28015).connect();
            System.out.println("DB CONNECTED");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("DB CONNECTION FAILED");
        }
        return true;
    }

    public static void disconnect() {
        conn.close();
        System.out.println("DISCONNECTED");
    }

    public static String get(String table, String where, String value, String column) {
        try {
            Cursor cursor = r.table(table).filter(row -> row.g(where.toLowerCase()).eq(value)).run(conn);
            if (cursor.hasNext()) {
                String sad = new JsonParser().parse(cursor.next().toString()).getAsJsonObject().get(column).toString();
                if (sad.startsWith("\"") && sad.endsWith("\"")) {
                    return sad.substring(1, sad.length()-1);
                } else {
                    return sad;
                }
            } else return null;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<3";
    }

    public static String update(String table, String whatvalue, String where, String wherevalue) {
        String out="";
        try {
            Cursor cursor = r.table(table).get(whatvalue).update(r.hashMap(where, wherevalue)).run(conn);
            out=cursor.toString();
        } catch (ClassCastException ignored) {}
        return out;
    }

    public static String insertServer(String id) {
        String out = "";
        try {
            Cursor cursor = r.table("server")
                    .insert(r.hashMap("id", id)
                            .with("prefix", "h.")
                    ).run(conn);
            out = cursor.next().toString();
        } catch (ClassCastException ignored) {}
        return out;
    }

    public static void setup() {
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
}
