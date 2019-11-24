package com.bbn.hadder.utils;

/*
 * @author Skidder / GregTCLTK
 */

import net.dv8tion.jda.api.audio.AudioSendHandler;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {

    @Override
    public boolean canProvide() {
        return false;
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {

        return null;
    }
}
