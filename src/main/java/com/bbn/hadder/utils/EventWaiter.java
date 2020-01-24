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

package com.bbn.hadder.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class EventWaiter {

    public void newOnMessageEventWaiter(Consumer<GuildMessageReceivedEvent> onEvent, JDA jda, User user) {
        Object listener = new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (user==null) {
                    onEvent.accept(event);
                    event.getJDA().getShardManager().removeEventListener(this);
                } else if (event.getAuthor().getId().equals(user.getId())) {
                    onEvent.accept(event);
                    event.getJDA().getShardManager().removeEventListener(this);
                }
            }
        };
        jda.getShardManager().addEventListener(listener);
    }

}
