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

package com.bbn.hadder.utils;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class Request {

    public static String get(String url) {

        OkHttpClient caller = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            try {
                JSONObject data = json.getJSONObject("data");
                JSONObject response1 = data.getJSONObject("response");
                return response1.toString().replace("{\"url\":\"", "").replace("\"}", "");
            } catch (Exception ignore) {
                return json.getString("url");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
