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
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RulesListener extends ListenerAdapter {

    private Rethink rethink;

    public RulesListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getMessageId().equals(rethink.getRulesMID(e.getGuild().getId())) && !e.getMember().getUser().isBot()) {
            if (e.getReactionEmote().isEmote()) {
                if (rethink.getRulesAEmote(e.getGuild().getId()).equals(e.getReactionEmote().getId())) {
                    addRole(e);
                } else if (rethink.getRulesDEmote(e.getGuild().getId()).equals(e.getReactionEmote().getId())) {
                    e.getReaction().removeReaction(e.getUser()).queue();
                    if (e.getGuild().getSelfMember().canInteract(e.getMember())) {
                        e.getMember().kick().reason("Declined the rules");
                    }
                }
            } else if (e.getReactionEmote().isEmoji()) {
                if (rethink.getRulesAEmote(e.getGuild().getId()).equals(e.getReactionEmote().getEmoji())) {
                    addRole(e);
                } else if (rethink.getRulesDEmote(e.getGuild().getId()).equals(e.getReactionEmote().getEmoji())) {
                    e.getReaction().removeReaction(e.getUser()).queue();
                    if (e.getGuild().getSelfMember().canInteract(e.getMember())) {
                        e.getMember().kick().reason("Declined the rules");
                    }
                }
            }
        }
    }

    private void addRole(MessageReactionAddEvent e) {
        if (e.getMember().getRoles().contains(e.getGuild().getRoleById(rethink.getRulesRID(e.getGuild().getId())))) {
            e.getGuild().removeRoleFromMember(e.getMember(), e.getGuild().getRoleById(rethink.getRulesRID(e.getGuild().getId()))).reason("Accepted rules").queue();
        } else e.getGuild().addRoleToMember(e.getMember(), e.getGuild().getRoleById(rethink.getRulesRID(e.getGuild().getId()))).reason("Accepted rules").queue();
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        if (e.getMessageId().equals(rethink.getRulesMID(e.getGuild().getId())) && !e.getMember().getUser().isBot()) {
                e.getGuild().removeRoleFromMember(e.getMember(), e.getGuild().getRoleById(rethink.getRulesRID(e.getGuild().getId()))).reason("Withdrawal of the acceptance of the rules").queue();
        }
    }
}
