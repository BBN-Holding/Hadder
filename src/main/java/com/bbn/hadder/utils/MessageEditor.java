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
                embedBuilder.setColor(Color.ORANGE);
                break;
        }
        return embedBuilder;
    }

    public enum Messagetype {
        ERROR,
        WARNING,
        INFO
    }

}
