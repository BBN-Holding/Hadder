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

package one.bbn.hadder.commands.owner;

import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.utils.MessageEditor.MessageType;

public class TestCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        e.getTextChannel().sendMessage(e.getMessageEditor().getMessage(MessageType.INFO, "commands.owner.test.success", "").build()).queue();
    }

    @Override
    public String[] labels() {
        return new String[]{"test"};
    }

    @Override
    public String description() {
        return "commands.owner.test.help.description";
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
