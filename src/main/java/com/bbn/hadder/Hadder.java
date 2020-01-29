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

package com.bbn.hadder;

import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.commands.general.*;
import com.bbn.hadder.commands.misc.*;
import com.bbn.hadder.commands.moderation.*;
import com.bbn.hadder.commands.nsfw.*;
import com.bbn.hadder.commands.owner.*;
import com.bbn.hadder.commands.fun.*;
import com.bbn.hadder.commands.settings.*;
import com.bbn.hadder.commands.music.*;
import com.bbn.hadder.core.*;
import com.bbn.hadder.listener.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;

import java.util.List;

public class Hadder {

    public static ShardManager shardManager;

    public static void main(String[] args) {
        startBot();
    }

    private static void startBot() {
        Config config = new Config("./config.json");
        if (!config.fileExists()) config.create();
        config.load();

        Rethink rethink = new Rethink(config);
        rethink.connect();

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();

        builder.setAutoReconnect(true);
        builder.setShardsTotal(1);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.setBulkDeleteSplittingEnabled(true);
        builder.setActivity(Activity.listening("to h.help"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setToken(config.getBotToken());

        HelpCommand helpCommand = new HelpCommand();
        AudioManager audioManager = new AudioManager();

        CommandHandler commandHandler = new CommandHandler(
                List.of(
                        helpCommand,
                        new TestCommand(),
                        new BanCommand(),
                        new UserPrefixCommand(),
                        new ShutdownCommand(),
                        new KickCommand(),
                        new PingCommand(),
                        new GifCommand(),
                        new ClearCommand(),
                        new GitHubCommand(),
                        new RebootCommand(),
                        new EqualsCommand(),
                        new InviteCommand(),
                        new ScreenShareCommand(),
                        new NickCommand(),
                        new PrefixCommand(),
                        new BlowjobCommand(),
                        new PornCommand(),
                        new AnalCommand(),
                        new CumCommand(),
                        new TransCommand(),
                        new PussyCommand(),
                        new BoobsCommand(),
                        new FeetCommand(),
                        new EroticCommand(),
                        new RoleCommand(),
                        new RulesCommand(),
                        new FeedbackCommand(),
                        new AvatarCommand(),
                        new EvalCommand(),
                        new JoinCommand(),
                        new LeaveCommand(),
                        new GuildLeaveCommand(),
                        new MemeCommand(),
                        new InviteDetectCommand(),
                        new BDSMCommand(),
                        new FingeringCommand(),
                        new LickingCommand(),
                        new SpankCommand(),
                        new RandomPornCommand(),
                        new SoloCommand(),
                        new RegionChangeCommand(),
                        new AboutCommand(),
                        new LanguageCommand(),
                        new ClydeCommand(),
                        new PlayCommand(),
                        new StarboardCommand(),
                        new QueueCommand(),
                        new InfoCommand(),
                        new SkipCommand(),
                        new EditRulesCommand(),
                        new VolumeCommand(),
                        new StopCommand(),
                        new BlacklistCommand(),
                        new PauseCommand(),
                        new LoopCommand(),
                        new BassCommand(),
                        new EchoCommand(),
                        new ServerStatsCommand()), config, helpCommand);

        builder.addEventListeners(
                new MentionListener(rethink),
                new PrivateMessageListener(rethink),
                new CommandListener(rethink, commandHandler, audioManager),
                new GuildListener(rethink, config),
                new ReadyListener(rethink, config),
                new InviteLinkListener(rethink),
                new RulesListener(rethink),
                new StarboardListener(rethink),
                new VoiceLeaveListener(audioManager));

        try {
            shardManager = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
