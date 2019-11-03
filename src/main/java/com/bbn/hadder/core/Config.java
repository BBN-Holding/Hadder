package com.bbn.hadder.core;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Config {

    private Path file;
    private JSONObject config;

    public Config(String path) {
        this.file = Paths.get(path);
    }

    public boolean fileExists() {
        return Files.exists(file);
    }

    public void load() {
        try {
            config = new JSONObject(new String(Files.readAllBytes(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (Files.notExists(file)) {
                file = Files.createFile(file);
            }
            Files.write(file, defaultConfigContent().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String defaultConfigContent() {
        return new JSONStringer().object()
                .key("Token").value(null)
                .key("Owners").value(new Long[]{477141528981012511L, 261083609148948488L})
                .key("Database").object()
                .key("IP").value("127.0.0.1")
                .key("Port").value(28015)
                .key("DBName").value("Hadder")
                .key("Username").value(null)
                .key("Password").value(null)
                .endObject().endObject().toString();
    }

    public String getToken() {
        return config.getString("Token");
    }

    public List<Object> getOwners() {
        return config.getJSONArray("Owners").toList();
    }

    public String getDatabaseIP() {
        return config.getJSONObject("Database").getString("IP");
    }

    public Integer getDatabasePort() {
        return config.getJSONObject("Database").getInt("Port");
    }

    public String getDatabaseName() {
        return config.getJSONObject("Database").getString("DBName");
    }

    public String getDatabaseUsername() {
        return config.getJSONObject("Database").getString("Username");
    }

    public String getDatabasePassword() {
        return config.getJSONObject("Database").getString("Password");
    }

}
