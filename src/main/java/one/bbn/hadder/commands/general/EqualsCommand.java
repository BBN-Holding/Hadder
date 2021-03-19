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

package com.bbn.hadder.commands.general;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;

public class EqualsCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        e.getChannel().sendMessage(
                e.getMessageEditor().getMessage(
                        MessageEditor.MessageType.INFO,
                        "commands.general.equals.string.first.request", "")
                        .build()).queue();
        new EventWaiter().newOnMessageEventWaiter(msge -> {
            String firstString = msge.getMessage().getContentRaw();
            e.getChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.INFO,
                            "commands.general.equals.string.second.request",
                            "").build()).queue();
            new EventWaiter().newOnMessageEventWaiter(msge2 -> {
                String secondString = msge2.getMessage().getContentRaw();
                e.getChannel().sendMessage(
                        e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO)
                                .setTitle((firstString.equals(secondString)) ? e.getMessageEditor().getTerm("commands.general.equals.string.equals.true") : e.getMessageEditor().getTerm("commands.general.equals.string.equals.false"))
                                .addField(e.getMessageEditor().getTerm("commands.general.equals.string.first"), firstString, false)
                                .addField(e.getMessageEditor().getTerm("commands.general.equals.string.second"), secondString, false)
                                .addField(e.getMessageEditor().getTerm("commands.general.equals.string.result"), String.valueOf(firstString.equals(secondString)), false)
                                .build()).queue();
            }, e.getJDA(), e.getAuthor());
        }, e.getJDA(), e.getAuthor());
    }

    @Override
    public String[] labels() {
        return new String[]{"equals"};
    }

    @Override
    public String description() {
        return "commands.general.equals.help.description";
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
