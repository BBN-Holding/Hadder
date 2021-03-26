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

package one.bbn.hadder.commands.fun;

import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.utils.MessageEditor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

public class MemeCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://meme-api.herokuapp.com/gimme").build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            String url = json.getString("url");
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(MessageEditor.MessageType.INFO,
                            "commands.fun.meme.success.title", "")
                            .setImage(url)
                            .setAuthor("Subreddit: " + json.getString("subreddit"))
                            .build()).queue();
        } catch (IOException ignore) {
            e.getTextChannel().sendMessage(
                    e.getMessageEditor().getMessage(
                            MessageEditor.MessageType.ERROR,
                            "error",
                            "commands.fun.meme.api.error")
                            .setColor(Color.RED)
                            .build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"meme"};
    }

    @Override
    public String description() {
        return "commands.fun.meme.help.description";
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
