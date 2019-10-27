package com.bbn.hadder.utils;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class MessageEditor {

    public EmbedBuilder setDefaultSettings(Messagetype type, EmbedBuilder embedBuilder) {
        switch (type) {
            case INFO:
                embedBuilder.setColor(new Color(47,94,105));
                break;

            case ERROR:
                embedBuilder.setColor(Color.RED);
                break;

            case WARNING:
                embedBuilder.setTitle("⚠ Warning ⚠").setColor(Color.ORANGE);
                break;

            case NO_PERMISSION:
                embedBuilder.setTitle("⛔ No Permission ⛔").setDescription("You are not authorized to execute this command!").setColor(Color.RED);
                break;

            case NO_SELF_PERMISSION:
                embedBuilder.setTitle("⛔ No Permission ⛔").setDescription("Unfortunately, I do not have the required rights to perform this action").setColor(Color.RED);
                break;
        }
        return embedBuilder;
    }

    public enum Messagetype {
        ERROR,
        WARNING,
        INFO,
        NO_PERMISSION,
        NO_SELF_PERMISSION
    }

}
