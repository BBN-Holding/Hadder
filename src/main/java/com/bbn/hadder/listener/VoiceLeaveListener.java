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

package com.bbn.hadder.listener;

import com.bbn.hadder.audio.AudioManager;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceLeaveListener extends ListenerAdapter {

    private AudioManager audioManager;

    public VoiceLeaveListener(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
        if (!e.getMember().getUser().isBot() && audioManager.hasPlayer(e.getGuild()) && e.getChannelLeft().getMembers().size() == 1 && e.getChannelLeft().getMembers().get(0).equals(e.getGuild().getSelfMember())) {
            audioManager.players.remove(e.getGuild().getId());
            audioManager.getPlayer(e.getGuild()).destroy();
            audioManager.getTrackManager(e.getGuild()).purgeQueue();
            e.getGuild().getAudioManager().closeAudioConnection();
        }
    }
}
