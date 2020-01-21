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

package com.bbn.hadder.commands.settings;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;

public class LanguageCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
            case "de":
                setLanguage("de", "German", e);
                break;
            case "en":
                setLanguage("en", "English", e);
                break;
            case "es":
                setLanguage("es", "Spanish", e);
                break;
            case "fr":
                setLanguage("fr", "French", e);
                break;
            case "ru":
                setLanguage("ru", "Russian", e);
                break;
            case "tr":
                setLanguage("tr", "Turkish", e);
                break;
            case "zh":
                setLanguage("zh", "Chinese", e);
                break;
            }
        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    public void setLanguage(String language_code, String language, CommandEvent e) {
        e.getRethinkUser().setLanguage(language_code);
        e.getTextChannel()
                .sendMessage(
                        e.getMessageEditor()
                                .getMessage(MessageEditor.MessageType.INFO, "commands.settings.language.success.title",
                                        "", "commands.settings.language.success.description", language)
                                .build())
                .queue();
        e.getRethinkUser().push();
    }

    @Override
    public String[] labels() {
        return new String[] { "language" };
    }

    @Override
    public String description() {
        return "commands.settings.language.help.description";
    }

    @Override
    public String usage() {
        return "[Language code]";
    }

    @Override
    public String example() {
        return "de";
    }
}
