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

package one.bbn.hadder.db;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import one.bbn.hadder.core.Config;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class Mongo {

    MongoClient client;
    MongoDatabase db;
    Config config;

    public Mongo(Config config) {
        this.config = config;
    }

    public void connect() {
        client = MongoClients.create("mongodb://" + config.getDatabaseUsername() + ":" + config.getDatabasePassword() + "@" + config.getDatabaseIP() + ":" + config.getDatabasePort() + "/?authSource=admin");
        db = client.getDatabase("Hadder");
    }

    public Object getByID(String collection_name, String where, String what, String column) {
        MongoCollection<Document> collection = db.getCollection(collection_name);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(where, what);
        FindIterable<Document> it = collection.find(whereQuery);
        return it.cursor().next().get(column);
    }

    public JSONObject getObjectByID(String collection, String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", id);
        String response = db.getCollection(collection).find(whereQuery).cursor().next().toJson();
        try {
            return new JSONObject(response);
        } catch (JSONException e) {
            insertUser(id);
            String response2 = db.getCollection(collection).find(whereQuery).cursor().next().toJson();
            try {
                return new JSONObject(response2);
            } catch (JSONException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public void remove(String table, String where, String value) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(where, value);
        client.getDatabase("VoiceAnalyzer").getCollection(table).deleteOne(whereQuery);
    }

    public void insert(String table, Document doc) {
        client.getDatabase("VoiceAnalyzer").getCollection(table).insertOne(doc);
    }

    public void insertUser(String id) {
        this.insert("user", new Document("id", id)
                .append("prefix", "h.")
                .append("language", "en")
                .append("blacklisted", "none"));
    }

    public void insertGuild(String id) {
        this.insert("server", new Document("id", id)
                .append("prefix", "h.")
                .append("message_id", "")
                .append("role_id", "")
                .append("invite_detect", false)
                .append("starboard", "")
                .append("neededstars", "4")
        );
    }

    public void push(MongoServer server) {
        Document object = new Document();
        for (Field field : server.getClass().getDeclaredFields()) {
            if (!field.getName().equals("mongo")) {
                try {
                    object.append(field.getName(), field.get(server));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", server.getId());

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", object);

        db.getCollection("server").updateOne(whereQuery, updateObject);
    }

    public void push(MongoUser user) {
        Document object = new Document();
        for (Field field : user.getClass().getDeclaredFields()) {
            if (!field.getName().equals("mongo")) {
                try {
                    object.append(field.getName(), field.get(user));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", user.getId());

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", object);

        db.getCollection("user").updateOne(whereQuery, updateObject);
    }

    public boolean hasStarboardMessage(String message_id) {
        this.getByID("stars", "id", message_id, "guild");
        return true;
    }

    public void insertStarboardMessage(String message_id, String guild_id, String starboard_message_id) {
        this.insert("stars", new Document("id", message_id).append("guild", guild_id).append("starboardmsg", starboard_message_id));
    }

    public String getStarboardMessage(String message_id) {
        return (String) this.getByID("stars", "id", message_id, "starboardmsg");
    }

    public void removeStarboardMessage(String message_id) {
        this.remove("stars", "id", message_id);
    }
}
