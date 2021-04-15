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

package one.bbn.hadder.commands.misc;

import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.utils.EventWaiter;
import one.bbn.hadder.utils.MessageEditor;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class FeedbackCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        e.getTextChannel().sendMessage(
                e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.misc.feedback.title.request.title",
                        "commands.misc.feedback.title.request.description")
                        .build()).queue();
        new EventWaiter().newOnMessageEventWaiter(e1 -> {
            String title = e1.getMessage().getContentDisplay();
            e1.getChannel().sendMessage(e.getMessageEditor().getMessage(
                    MessageEditor.MessageType.INFO,
                    "commands.misc.feedback.description.request.title",
                    "commands.misc.feedback.description.request.description")
                    .build()).queue();
            new EventWaiter().newOnMessageEventWaiter(e2 -> {
                String description = e2.getMessage().getContentDisplay();
                try {
                    GitHub connection = GitHub.connectUsingOAuth(e.getConfig().getGitHubToken());
                    GHRepository Hadder = connection.getOrganization("BBN-Holding").getRepository("Hadder");
                    GHIssue issue = Hadder.createIssue(title).body("<strong>Feedback by " + e.getAuthor().getAsTag() + "</strong><br>" + description).label("feedback").create();
                    issue.addLabels("feedback");
                    e.getTextChannel().sendMessage(
                            e.getMessageEditor().getMessage(
                                    MessageEditor.MessageType.INFO,
                                    "commands.misc.feedback.success.title",
                                    "")
                                    .setDescription(issue.getHtmlUrl().toString())
                                    .build()).queue();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }, e.getJDA(), e.getAuthor());
        }, e.getJDA(), e.getAuthor());

    }

    @Override
    public String[] labels() {
        return new String[]{"feedback"};
    }

    @Override
    public String description() {
        return "commands.misc.feedback.help.description";
    }

    @Override
    public String usage() {
        return null;
    }

    @Override
    public String example() {
        return null;
    }
}
