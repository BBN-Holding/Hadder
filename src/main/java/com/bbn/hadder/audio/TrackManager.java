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

package com.bbn.hadder.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer player;
    private final AudioManager manager;
    private final Queue<AudioInfo> queue;
    private AudioTrack lastTrack;
    private boolean loop = false;

    public TrackManager(AudioPlayer player, AudioManager manager) {
        this.manager = manager;
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);

        if (player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();
        VoiceChannel vChan = info.getAuthor().getVoiceState().getChannel();
        if (vChan == null) {
            player.stopTrack();
        } else {
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChan);
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (loop) {
            this.lastTrack = track;
            player.playTrack(lastTrack.makeClone());
        } else {
            Guild g = queue.poll().getAuthor().getGuild();
            manager.getPlayer(g).stopTrack();
            if (!queue.isEmpty()) player.playTrack(queue.element().getTrack().makeClone());
            else {
                manager.players.remove(g.getId());
                manager.getPlayer(g).destroy();
                manager.getTrackManager(g).purgeQueue();
                g.getAudioManager().closeAudioConnection();
            }
        }
    }

    public Set<AudioInfo> getQueuedTracks() {
        return new LinkedHashSet<>(queue);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void remove(AudioInfo entry) {
        queue.remove(entry);
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean repeating) {
        this.loop = repeating;
    }
}
