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
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Random;

public class MentionListener extends ListenerAdapter {

    private Rethink rethink;

    public MentionListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        if (e.isFromType(ChannelType.TEXT) && (e.getMessage().getContentRaw().equals(e.getGuild().getSelfMember().getAsMention())||
                e.getMessage().getContentRaw().equals(e.getGuild().getSelfMember().getAsMention().replace("@", "@!")))) {
            e.getChannel().sendMessage(new MessageEditor(rethink, e.getAuthor()).getMessage(MessageEditor.MessageType.INFO)
                    .setTitle("Hello I'm Hadder.")
                    .setAuthor(e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser().getAvatarUrl(), e.getJDA().getSelfUser().getAvatarUrl())
                    .addField("Users", String.valueOf(e.getJDA().getUsers().size()), false)
                    .addField("Guilds", String.valueOf(e.getJDA().getGuilds().size()), false)
                    .addField("Prefix (User)", rethink.getUserPrefix(e.getAuthor().getId()), false)
                    .addField("Prefix (Guild)", rethink.getGuildPrefix(e.getGuild().getId()), false)
                    .build()).queue();
        } else if (e.getMessage().getContentRaw().equalsIgnoreCase("@someone")) {
            int member = new Random().nextInt(e.getGuild().getMembers().size()-1);
            if (member>0&&member<e.getGuild().getMembers().size()) {
                e.getChannel().sendMessage(e.getGuild().getMembers().get(member).getAsMention()+ " (Executed by: "+e.getAuthor().getAsTag()+")").queue();
            }
        }
    }
}
