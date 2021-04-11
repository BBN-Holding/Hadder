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
import one.bbn.hadder.db.Mongo;
import one.bbn.hadder.db.MongoServer;
import one.bbn.hadder.db.MongoUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Random;

public class MentionListener extends ListenerAdapter {

    private final Mongo mongo;
    private final Config config;

    public MentionListener(Mongo mongo, Config config) {
        this.mongo = mongo;
        this.config = config;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        if (!e.getAuthor().isBot() && e.isFromType(ChannelType.TEXT)) {
            MongoServer mongoServer = new MongoServer(mongo.getObjectByID("server", e.getGuild().getId()), mongo);
            MongoUser mongoUser = new MongoUser(mongo.getObjectByID("user", e.getAuthor().getId()), mongo);
            if (e.isFromType(ChannelType.TEXT) && (e.getMessage().getContentRaw().equals(e.getGuild().getSelfMember().getAsMention()) ||
                    e.getMessage().getContentRaw().equals(e.getGuild().getSelfMember().getAsMention().replace("@", "@!")))) {

                String version = null;

                try {
                    GitHub connection = GitHub.connectUsingOAuth(config.getGitHubToken());
                    GHRepository Hadder = connection.getOrganization("BBN-Holding").getRepository("Hadder");
                    version = Hadder.getLatestRelease().getTagName();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("Hi!")
                        .addField("Version", version, false)
                        .addField("User-Prefix", mongoUser.getPrefix(), true)
                        .addField("Guild-Prefix", mongoServer.getPrefix(), true)
                        .addField("Join our Dev Server!", "[Click here!](https://discord.gg/nPwjaJk)", true)
                        .addField("Github", "[Click here!](https://github.com/BBN-Holding/Hadder)", false)
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
