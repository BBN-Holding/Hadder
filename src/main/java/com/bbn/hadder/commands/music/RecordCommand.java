/*
 * Copyright 2019-2020 GregTCLTK and Schlauer-Hax
 *
 * Licensed under the GNU Affero General Public License, Version 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.gnu.org/licenses/agpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bbn.hadder.commands.music;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.UserAudio;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Perms(Perm.BOT_OWNER)
public class RecordCommand implements Command {

    private final HashMap<String, Queue<byte[]>> queue = new HashMap<>();

    @Override
    public void executed(String[] args, CommandEvent event) {
        Guild guild = event.getMember().getVoiceState().getChannel().getGuild();
        AudioManager audioManager = guild.getAudioManager();
        EchoHandler handler = new EchoHandler(queue);
        if (!audioManager.isConnected()) {
            audioManager.setSendingHandler(handler);
            audioManager.setReceivingHandler(handler);
            audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
        } else {
            for (Map.Entry<String, Queue<byte[]>> key : queue.entrySet()) {
                File file = new File("./"+event.getJDA().getUserById(key.getKey())+".wav");


            }
        }
    }

    public static class EchoHandler implements AudioSendHandler, AudioReceiveHandler {

        private HashMap<String, Queue<byte[]>> queue;

        public EchoHandler(HashMap<String, Queue<byte[]>> queue) {
            this.queue = queue;
        }

        @Override
        public boolean canProvide() {
            return false;
        }

        @Nullable
        @Override
        public ByteBuffer provide20MsAudio() {
            return null;
        }

        @Override
        public void handleUserAudio(@Nonnull UserAudio userAudio) {
            if (queue.containsKey(userAudio.getUser().getId())) {
                queue.get(userAudio.getUser().getId()).add(userAudio.getAudioData(1.0f));
            } else {
                Queue<byte[]> newqueue = new ConcurrentLinkedQueue<>();
                newqueue.add(userAudio.getAudioData(1.0f));
                queue.put(userAudio.getUser().getId(), newqueue);
            }
        }

        public HashMap<String, Queue<byte[]>> getQueue() {
            return queue;
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"record"};
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
