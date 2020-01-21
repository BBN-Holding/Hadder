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

package com.bbn.hadder.commands.fun;

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class GifCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            StringBuilder query = new StringBuilder();
            for (String arg : args) {
                query.append(arg.toLowerCase()).append("+");
                query = new StringBuilder(query.substring(0, query.length() - 1));
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=" + e.getConfig().getGiphyToken()).build();
            try {
                Random rand = new Random();
                Response response = client.newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                JSONArray array = json.getJSONArray("data");
                int gifIndex = rand.nextInt(array.length());
                String url = array.getJSONObject(gifIndex).get("url").toString();
                e.getTextChannel().sendMessage(url).queue();
            } catch (Exception ignore) {
                e.getTextChannel().sendMessage(
                        e.getMessageEditor().getMessage(
                                MessageEditor.MessageType.ERROR,
                                "error",
                                "commands.fun.gif.error.description").build()).queue();
            }
        } else e.getHelpCommand().sendHelp(this, e);
    }

    @Override
    public String[] labels() {
        return new String[]{"gif"};
    }

    @Override
    public String description() {
        return "commands.fun.gif.help.description";
    }

    @Override
    public String usage() {
        return "[Term]";
    }

    @Override
    public String example() {
        return "Cute cat";
    }
}
