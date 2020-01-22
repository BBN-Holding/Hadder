/*
 * @author Hax / Hax6775 / Schlauer_Hax
 */

package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import net.dv8tion.jda.api.audio.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Perms(Perm.BOT_OWNER)
public class EchoCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        Guild guild = event.getMember().getVoiceState().getChannel().getGuild();
        AudioManager audioManager = guild.getAudioManager();
        EchoHandler handler = new EchoHandler();
        if (!audioManager.isConnected()) {
            audioManager.setSendingHandler(handler);
            audioManager.setReceivingHandler(handler);
            audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
        } else {
            audioManager.closeAudioConnection();
        }
    }

    public static class EchoHandler implements AudioSendHandler, AudioReceiveHandler {

        private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();

        @Override
        public boolean canProvide() {
            return !queue.isEmpty();
        }

        @Nullable
        @Override
        public ByteBuffer provide20MsAudio() {
            byte[] data = queue.poll();
            return data == null ? null : ByteBuffer.wrap(data); // Wrap this in a java.nio.ByteBuffer
        }

        @Override
        public boolean canReceiveCombined() {
            return queue.size() < 10;
        }

        @Override
        public void handleCombinedAudio(@Nonnull CombinedAudio combinedAudio) {
            if (combinedAudio.getUsers().isEmpty())
                return;

            byte[] data = combinedAudio.getAudioData(1.0f); // volume at 100% = 1.0 (50% = 0.5 / 55% = 0.55)
            queue.add(data);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"echo"};
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }

}
