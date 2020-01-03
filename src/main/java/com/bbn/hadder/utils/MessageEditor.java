package com.bbn.hadder.utils;

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageEditor {

    private Rethink rethink;
    private User user;

    public MessageEditor(Rethink rethink, User user) {
        this.rethink = rethink;
        this.user = user;
    }

    public EmbedBuilder getMessage(MessageType type) {
        return this.getMessage(type, "", "", "", "", "", "");
    }

    public EmbedBuilder getMessage(MessageType type, String title, String description) {
        return this.getMessage(type, title, "", "", description, "", "");
    }

    public EmbedBuilder getMessage(MessageType type, String title, String title_extra,
                                   String description, String description_extra) {
        return this.getMessage(type, title, title_extra, "", description, description_extra, "");
    }

    public EmbedBuilder getMessage(MessageType type, String title, String title_extra, String title_extra_two,
                            String description, String description_extra, String description_extra_two) {
        String language = (this.user!=null) ? rethink.getLanguage(this.user.getId()) : null;
        EmbedBuilder eb = this.getDefaultSettings(type);
        if (!"".equals(title)) eb.setTitle(this.handle(language, title, title_extra, title_extra_two));
        if (!"".equals(description)) eb.setDescription(this.handle(language, description, description_extra, description_extra_two));
        return eb;
    }

    public enum MessageType {
        ERROR,
        WARNING,
        INFO,
        NO_PERMISSION,
        NO_SELF_PERMISSION,
        NO_NSFW
    }

    private EmbedBuilder getDefaultSettings(MessageType type) {
        EmbedBuilder builder = new EmbedBuilder();
        switch (type) {
            case INFO:
                builder
                        .setColor(new Color(78, 156, 174))
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case ERROR:
                builder
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case WARNING:
                builder
                        .setColor(Color.ORANGE)
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case NO_PERMISSION:
                builder
                        .setTitle("⛔ No Permission ⛔")
                        .setDescription("You are not authorized to execute this command!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case NO_SELF_PERMISSION:
                builder
                        .setTitle("⛔ No Permission ⛔")
                        .setDescription("Unfortunately, I do not have the required rights to perform this action!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case NO_NSFW:
                builder
                        .setTitle("⛔ No NSFW ⛔")
                        .setDescription("You can only execute this command in NSFW channels!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;
        }
        return builder;
    }

    public String getTerm(String string) {
        return this.handle(rethink.getLanguage(user.getId()), string, "", "");
    }

    public String getTerm(String string, String extra, String extra_two) {
        return this.handle(rethink.getLanguage(user.getId()), string, extra, extra_two);
    }

    private String handle(String language_code, String string, String extra, String extra_two) {
        Locale locale = new Locale(language_code);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translations/Translations", locale);
        if (resourceBundle.containsKey(string))
            return resourceBundle.getString(string).replaceAll("%extra%", extra).replaceAll("%extra_two%", extra_two);
        else return "This key doesn't exist. Please report this to the Bot Developers. Key: " + string + " Language_code: " + language_code;
    }
}
