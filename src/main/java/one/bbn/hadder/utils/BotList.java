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

package one.bbn.hadder.utils;

import one.bbn.hadder.Hadder;
import one.bbn.hadder.core.Config;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotList {

    private static String BotList = "https://";

    private Config config;

    public BotList(Config config) {
        this.config = config;
    }

    public void post() {
//        if (Files.notExists(Paths.get("./DEBUG"))) {
//            JSONObject json = new JSONObject();
//            json.put("server_count", Hadder.shardManager.getGuilds().size());
//            json.put("guildCount", Hadder.shardManager.getGuilds().size());
//            json.put("guilds", Hadder.shardManager.getGuilds().size());
//            json.put("count", Hadder.shardManager.getGuilds().size());
//            json.put("users", Hadder.shardManager.getUsers().size());
//            json.put("shard_count", Hadder.shardManager.getShards().size());
//            json.put("shardCount", Hadder.shardManager.getShards().size());
//            json.put("member_count", Hadder.shardManager.getUsers().size());
//
//            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
//
//            // Mythical Bot List
//
//            Request botlist = new Request.Builder()
//                    .url(BotList)
//                    .post(body)
//                    .addHeader("Authorization", config.getBotListToken())
//                    .build();
//
//            try {
//                new OkHttpClient().newCall(botlist).execute().close();
//                System.out.println("Successfully posted count for the !");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
