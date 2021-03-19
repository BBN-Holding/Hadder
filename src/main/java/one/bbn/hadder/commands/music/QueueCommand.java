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

import com.bbn.hadder.audio.AudioInfo;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

import java.util.Set;

public class QueueCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (!e.getAudioManager().hasPlayer(e.getGuild()) || e.getAudioManager().getTrackManager(e.getGuild()).getQueuedTracks().isEmpty()) {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.ERROR,
                    "commands.music.queue.error.title",
                    "commands.music.queue.error.description"
            ).build()).queue();
        } else {
            Set<AudioInfo> queue = e.getAudioManager().getTrackManager(e.getGuild()).getQueuedTracks();
            StringBuilder builder = new StringBuilder();
            long queuelength = 0;
            for (AudioInfo g : queue) {
                queuelength = queuelength + g.getTrack().getInfo().length;
                builder.append("(" + e.getAudioManager().getTimestamp(g.getTrack().getInfo().length) + ") **").append(g.getTrack().getInfo().author).append("**: `").append(g.getTrack().getInfo().title).append("` \n");
            }
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.queue.success.title", "(" + String.valueOf(e.getAudioManager().getTimestamp(queuelength)) + ")",
                    "commands.music.queue.success.description", builder.toString())
                    .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"queue"};
    }

    @Override
    public String description() {
        return "commands.music.queue.help.description";
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
