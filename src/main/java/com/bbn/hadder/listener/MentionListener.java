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
import com.bbn.hadder.RethinkUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MentionListener extends ListenerAdapter {

    private Rethink rethink;

    public MentionListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        if (!e.getAuthor().isBot()) {
            RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", e.getGuild().getId()), rethink);
            RethinkUser rethinkUser = new RethinkUser(rethink.getObjectByID("user", e.getAuthor().getId()), rethink);
            if (e.isFromType(ChannelType.TEXT) && (e.getMessage().getContentRaw().equals(e.getGuild().getSelfMember().getAsMention()) ||
                    e.getMessage().getContentRaw().equals(e.getGuild().getSelfMember().getAsMention().replace("@", "@!")))) {

                MavenXpp3Reader reader = new MavenXpp3Reader();
                Model model = null;
                try {
                    model = reader.read(new FileReader("pom.xml"));
                } catch (IOException | XmlPullParserException ex) {
                    ex.printStackTrace();
                }
                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("Hi!")
                        .addField("Version", model.getVersion(), false)
                        .addField("User-Prefix", rethinkUser.getPrefix(), true)
                        .addField("Guild-Prefix", rethinkServer.getPrefix(), true);
                StringBuilder stringBuilder = new StringBuilder();
                model.getDependencies().forEach(
                        dependency -> stringBuilder.append(dependency.getArtifactId()).append(" - ").append(dependency.getVersion()).append("\n")
                );
                builder.addField("Dependencies", stringBuilder.toString(), false);
                StringBuilder devs = new StringBuilder();
                //TODO: Fix Mail stuff
                model.getDevelopers().forEach(
                        developer -> devs.append(developer.getId()).append(" - [Website](").append(developer.getUrl()).append("), [E-Mail](https://hax.bigbotnetwork.de/redirect.html?url=mailto:").append(developer.getEmail()).append(")\n")
                );
                builder.addField("Developer", devs.toString(), false);
                builder.addField("Join our Dev Server!", "[Click here!](https://discord.gg/58My2dM)", true);
                builder.addField("Github", "[Click here!](https://github.com/BigBotNetwork/Hadder)",false);
                builder.addField("Twitch", "[Click here!](https://www.twitch.tv/bigbotnetwork)", false);
                e.getChannel().sendMessage(builder.build()).queue();
            } else if (e.getMessage().getContentRaw().equalsIgnoreCase("@someone")) {
                int member = new Random().nextInt(e.getGuild().getMembers().size() - 1);
                if (member > 0 && member < e.getGuild().getMembers().size()) {
                    e.getChannel().sendMessage(e.getGuild().getMembers().get(member).getAsMention() + " (Executed by: " + e.getAuthor().getAsTag() + ")").queue();
                }
            }
        }
    }
}
