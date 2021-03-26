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

package one.bbn.hadder.listener;

import one.bbn.hadder.core.Config;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import one.bbn.hadder.utils.BotList;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    private final Config config;

    public ReadyListener(Config config) {
        this.config = config;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent e) {
        new BotList(config).post();
    }
}
