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

import one.bbn.hadder.core.Config;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class OwnerMessageListener extends ListenerAdapter {

    Config config;

    public OwnerMessageListener(Config config) {
        this.config = config;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (config.getOwners().contains(event.getAuthor().getIdLong()) && event.getMessage().getContentRaw().startsWith(":") && event.getMessage().getContentRaw().endsWith(":")) {
            String emoteName = event.getMessage().getContentRaw().split(":")[1];
            if (!emoteName.contains(" ")) {
                Emote[] emotes = event.getJDA().getEmotesByName(emoteName, true).toArray(new Emote[0]);
                StringBuilder sb = new StringBuilder();
                if (emotes.length != 0) {
                    for (Emote emote : emotes) {
                        sb.append(emote.getAsMention()).append(" ");
                    }
                    event.getChannel().sendMessage(sb.toString()).queue();
                }
            }
        }
    }
}
