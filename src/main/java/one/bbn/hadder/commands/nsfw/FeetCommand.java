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

package one.bbn.hadder.commands.nsfw;

import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.utils.Http;
import one.bbn.hadder.utils.MessageEditor;

public class FeetCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (e.getTextChannel().isNSFW()) {

            String url = Http.getNSFW("https://api.nekos.dev/api/v3/images/nsfw/gif/feet");

            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                    .setAuthor(e.getMessageEditor().getTerm("commands.nsfw.gif.error.title"), url)
                    .setImage(url)
                    .setFooter("Feet")
                    .build()).queue();

        } else {
            e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageEditor.MessageType.NO_NSFW).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"feet"};
    }

    @Override
    public String description() {
        return "commands.nsfw.feet.help.description";
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
