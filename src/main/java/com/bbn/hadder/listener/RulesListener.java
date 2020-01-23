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
import com.bbn.hadder.RethinkServer;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RulesListener extends ListenerAdapter {

    private Rethink rethink;

    public RulesListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", event.getGuild().getId()), rethink);
        if (event.getMessageId().equals(rethinkServer.getMessage_id()) && !event.getMember().getUser().isBot()) {
            if (event.getReactionEmote().isEmote()) {
                if (rethinkServer.getAccept_emote().equals(event.getReactionEmote().getId())) {
                    addRole(event);
                } else if (rethinkServer.getDecline_emote().equals(event.getReactionEmote().getId())) {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if (event.getGuild().getSelfMember().canInteract(event.getMember())) {
                        event.getMember().kick().reason("Declined the rules");
                    }
                }
            } else if (event.getReactionEmote().isEmoji()) {
                if (rethinkServer.getAccept_emote().equals(event.getReactionEmote().getEmoji())) {
                    addRole(event);
                } else if (rethinkServer.getDecline_emote().equals(event.getReactionEmote().getEmoji())) {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if (event.getGuild().getSelfMember().canInteract(event.getMember())) {
                        event.getMember().kick().reason("Declined the rules");
                    }
                }
            }
        }
    }

    private void addRole(MessageReactionAddEvent event) {
        RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", event.getGuild().getId()), rethink);
        if (event.getMember().getRoles().contains(event.getGuild().getRoleById(rethinkServer.getRole_id()))) {
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rethinkServer.getRole_id())).reason("Accepted rules").queue();
        } else event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rethinkServer.getRole_id())).reason("Accepted rules").queue();
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", event.getGuild().getId()), rethink);
        if (event.getMessageId().equals(rethinkServer.getMessage_id()) && !event.getMember().getUser().isBot()) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rethinkServer.getRole_id())).reason("Withdrawal of the acceptance of the rules").queue();
        }
    }
}
