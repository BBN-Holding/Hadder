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

package one.bbn.hadder.listener;

import one.bbn.hadder.db.Mongo;
import one.bbn.hadder.db.MongoUser;
import one.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

public class PrivateMessageListener extends ListenerAdapter {

    private final Mongo mongo;

    public PrivateMessageListener(Mongo mongo) {
        this.mongo = mongo;
    }

    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.isFromType(ChannelType.PRIVATE) && !e.getAuthor().getId().equals(e.getJDA().getSelfUser().getId())) {
            PrivateChannel Skidder = e.getJDA().getUserById("401817301919465482").openPrivateChannel().complete();
            PrivateChannel Hax = e.getJDA().getUserById("261083609148948488").openPrivateChannel().complete();
            MongoUser HaxUser = new MongoUser(mongo.getObjectByID("user", "261083609148948488"), mongo);
            MongoUser SkidderUser = new MongoUser(mongo.getObjectByID("user", "261083609148948488"), mongo);

            Skidder.sendMessage(new MessageEditor(SkidderUser, e.getJDA().getUserById("401817301919465482")).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + e.getAuthor().getAsTag())
                    .setAuthor(e.getAuthor().getName(), e.getAuthor().getAvatarUrl(), e.getAuthor().getAvatarUrl())
                    .setDescription(e.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();
            Hax.sendMessage(new MessageEditor(HaxUser, e.getJDA().getUserById("261083609148948488")).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("New DM by " + e.getAuthor().getAsTag())
                    .setAuthor(e.getAuthor().getName(), e.getAuthor().getAvatarUrl(), e.getAuthor().getAvatarUrl())
                    .setDescription(e.getMessage().getContentRaw())
                    .setTimestamp(Instant.now())
                    .build()).queue();

            e.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("No DM support")
                    .setDescription("You have to execute your commands on a guild! For further support join our Discord server [here](https://discord.gg/nPwjaJk)")
                    .setColor(Color.RED)
                    .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                    .setTimestamp(Instant.now())
                    .build()).queue();
        }
    }
}
