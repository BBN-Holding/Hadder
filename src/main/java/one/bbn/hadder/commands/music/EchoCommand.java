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

package one.bbn.hadder.commands.music;

import one.bbn.hadder.audio.AudioPlayerSendHandler;
import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

public class EchoCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        Guild guild = e.getMember().getVoiceState().getChannel().getGuild();
        AudioManager audioManager = guild.getAudioManager();
        AudioPlayerSendHandler handler = new AudioPlayerSendHandler(e.getAudioManager().getPlayer(e.getGuild()));
        if (!audioManager.isConnected()) {
            audioManager.setSendingHandler(handler);
            audioManager.setReceivingHandler(handler);
            audioManager.openAudioConnection(e.getMember().getVoiceState().getChannel());
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                    "commands.music.echo.success.title",
                    "commands.music.echo.success.description")
                    .build()).queue();
        } else {
            audioManager.closeAudioConnection();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"echo"};
    }

    @Override
    public String description() {
        return "commands.music.echo.help.description";
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
