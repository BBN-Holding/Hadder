/*
 * Copyright 2019-2021 GregTCLTK and Schlauer-Hax
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

package one.bbn.hadder.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioPlayerSendHandler implements AudioSendHandler, AudioReceiveHandler {

    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;
    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean canProvide() {
        if (audioPlayer.getPlayingTrack() == null)
            return !queue.isEmpty();
        else if (lastFrame == null) {
            lastFrame = audioPlayer.provide();
            return lastFrame != null;
        }
        return false;
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        if (audioPlayer.getPlayingTrack() == null) {
            byte[] data = queue.poll();
            return data == null ? null : ByteBuffer.wrap(data);
        } else {
            if (lastFrame == null) {
                lastFrame = audioPlayer.provide();
            }

            byte[] data = lastFrame != null ? lastFrame.getData() : null;
            lastFrame = null;

            return ByteBuffer.wrap(data);
        }
    }

    @Override
    public boolean canReceiveCombined() {
        return queue.size() < 10;
    }

    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        if (combinedAudio.getUsers().isEmpty())
            return;

        byte[] data = combinedAudio.getAudioData(1.0f);
        queue.add(data);
    }

    @Override
    public boolean isOpus() {
        return audioPlayer.getPlayingTrack() != null;
    }
}
