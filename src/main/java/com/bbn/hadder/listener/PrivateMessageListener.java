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

import com.bbn.hadder.Rethink;
import com.bbn.hadder.RethinkUser;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

public class PrivateMessageListener extends ListenerAdapter {

    private Rethink rethink;

    public PrivateMessageListener(Rethink rethink) {
        this.rethink = rethink;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            PrivateChannel Skidder = event.getJDA().getUserById("477141528981012511").openPrivateChannel().complete();
            PrivateChannel Hax = event.getJDA().getUserById("261083609148948488").openPrivateChannel().complete();
            RethinkUser rethinkUser = new RethinkUser(rethink.getObjectByID("user", "261083609148948488"), rethink);

            Skidder.sendMessage(new MessageEditor(rethinkUser, event.getJDA().getUserById("261083609148948488")).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + event.getAuthor().getAsTag())
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                    .setDescription(event.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();
            Hax.sendMessage(new MessageEditor(rethinkUser, event.getJDA().getUserById("261083609148948488")).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + event.getAuthor().getAsTag())
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                    .setDescription(event.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("No DM support")
                    .setDescription("You have to execute your commands on a guild!")
                    .setColor(Color.RED)
                    .setFooter("Hadder", "https://bigbotnetwork.com/images/Hadder.png")
                    .setTimestamp(Instant.now())
                    .build()).queue();
        }
    }
}
