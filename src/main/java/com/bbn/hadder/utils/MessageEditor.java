package com.bbn.hadder.utils;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageEditor {

    public EmbedBuilder setDefaultSettings(MessageType type) {
        EmbedBuilder builder = new EmbedBuilder();
        switch (type) {
            case INFO:
                builder
                        .setColor(new Color(47, 94, 105))
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
        }
        return builder;
    }

    public enum MessageType {
        ERROR,
        WARNING,
        INFO,
        NO_PERMISSION,
        NO_SELF_PERMISSION
    }

    public static String handle(String language_code, String string) {
        Locale locale = new Locale(language_code);
        return ResourceBundle.getBundle("Translations/Translations", locale).getString(string).replaceAll("%prefix%", "h.");
    }
}
