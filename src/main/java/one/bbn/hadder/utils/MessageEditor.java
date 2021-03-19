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

package one.bbn.hadder.utils;

import one.bbn.hadder.db.RethinkUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageEditor {

    private final RethinkUser rethinkUser;
    private final User user;

    public MessageEditor(RethinkUser rethinkUser, User user) {
        this.rethinkUser = rethinkUser;
        this.user = user;
    }

    public EmbedBuilder getMessage(MessageType type) {
        return this.getMessage(type, "", "", "", "", "", "");
    }

    public EmbedBuilder getMessage(MessageType type, String title, String description) {
        return this.getMessage(type, title, "", "", description, "", "");
    }

    public EmbedBuilder getMessage(MessageType type, String title, String title_extra,
                                   String description, String description_extra) {
        return this.getMessage(type, title, title_extra, "", description, description_extra, "");
    }

    public EmbedBuilder getMessage(MessageType type, String title, String title_extra, String title_extra_two,
                                   String description, String description_extra, String description_extra_two) {
        String language = (this.user != null) ? rethinkUser.getLanguage() : null;
        EmbedBuilder eb = this.getDefaultSettings(type);
        if (!"".equals(title)) eb.setTitle(this.handle(language, title, title_extra, title_extra_two));
        if (!"".equals(description))
            eb.setDescription(this.handle(language, description, description_extra, description_extra_two));
        return eb;
    }

    public enum MessageType {
        ERROR,
        WARNING,
        INFO,
        NO_PERMISSION,
        NO_SELF_PERMISSION,
        NO_NSFW
    }

    private EmbedBuilder getDefaultSettings(MessageType type) {
        EmbedBuilder builder = new EmbedBuilder();
        switch (type) {
            case INFO:
                builder
                        .setColor(new Color(78, 156, 174))
                        .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case ERROR:
                builder
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case WARNING:
                builder
                        .setColor(Color.ORANGE)
                        .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case NO_PERMISSION:
                builder
                        .setTitle("⛔ No Permission ⛔")
                        .setDescription("You are not authorized to execute this command!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case NO_SELF_PERMISSION:
                builder
                        .setTitle("⛔ No Permission ⛔")
                        .setDescription("Unfortunately, I do not have the required rights to perform this action!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;

            case NO_NSFW:
                builder
                        .setTitle("⛔ No NSFW ⛔")
                        .setDescription("You can only execute this command in NSFW channels!")
                        .setColor(Color.RED)
                        .setFooter("Hadder", "https://bbn.one/images/Hadder.png")
                        .setTimestamp(Instant.now());
                break;
        }
        return builder;
    }

    public String getTerm(String string) {
        return this.handle(rethinkUser.getLanguage(), string, "", "");
    }

    public String getTerm(String string, String extra, String extra_two) {
        return this.handle(rethinkUser.getLanguage(), string, extra, extra_two);
    }

    private String handle(String language_code, String string, String extra, String extra_two) {
        Locale locale = new Locale(language_code);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Translations/Translations", locale);
        if (resourceBundle.containsKey(string))
            return resourceBundle.getString(string).replaceAll("%extra%", extra).replaceAll("%extra_two%", extra_two);
        else
            return "This key doesn't exist. Please report this to the Bot Developers. Key: `" + string + "` Language_code: `" + language_code + "`";
    }
}
