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
import one.bbn.hadder.db.MongoServer;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RulesListener extends ListenerAdapter {

    private final Mongo mongo;

    public RulesListener(Mongo mongo) {
        this.mongo = mongo;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        MongoServer mongoServer = new MongoServer(mongo.getObjectByID("server", e.getGuild().getId()), mongo);
        if (e.getMessageId().equals(mongoServer.getMessageID()) && !e.getUser().isBot()) {
            if (e.getReactionEmote().isEmote()) {
                if (mongoServer.getAcceptEmote().equals(e.getReactionEmote().getId())) {
                    addRole(e);
                } else if (mongoServer.getDeclineEmote().equals(e.getReactionEmote().getId())) {
                    e.getReaction().removeReaction(e.getUser()).queue();
                    if (e.getGuild().getSelfMember().canInteract(e.getMember())) {
                        e.getMember().kick().reason("Declined the rules");
                    }
                }
            } else if (e.getReactionEmote().isEmoji()) {
                if (mongoServer.getAcceptEmote().equals(e.getReactionEmote().getEmoji())) {
                    addRole(e);
                } else if (mongoServer.getDeclineEmote().equals(e.getReactionEmote().getEmoji())) {
                    e.getReaction().removeReaction(e.getUser()).queue();
                    if (e.getGuild().getSelfMember().canInteract(e.getMember())) {
                        e.getMember().kick().reason("Declined the rules");
                    }
                }
            }
        }
    }

    private void addRole(MessageReactionAddEvent e) {
        MongoServer mongoServer = new MongoServer(mongo.getObjectByID("server", e.getGuild().getId()), mongo);
        if (e.getMember().getRoles().contains(e.getGuild().getRoleById(mongoServer.getRoleID()))) {
            e.getGuild().removeRoleFromMember(e.getMember(), e.getGuild().getRoleById(mongoServer.getRoleID())).reason("Accepted rules").queue();
        } else
            e.getGuild().addRoleToMember(e.getMember(), e.getGuild().getRoleById(mongoServer.getRoleID())).reason("Accepted rules").queue();
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        MongoServer mongoServer = new MongoServer(mongo.getObjectByID("server", e.getGuild().getId()), mongo);
        if (e.getMessageId().equals(mongoServer.getMessageID()) && !e.getUser().isBot()) {
            e.getGuild().removeRoleFromMember(e.getMember(), e.getGuild().getRoleById(mongoServer.getRoleID())).reason("Withdrawal of the acceptance of the rules").queue();
        }
    }
}
