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

package one.bbn.hadder;

import net.dv8tion.jda.api.utils.cache.CacheFlag;
import one.bbn.hadder.audio.AudioManager;
import one.bbn.hadder.commands.fun.AvatarCommand;
import one.bbn.hadder.commands.fun.ClydeCommand;
import one.bbn.hadder.commands.fun.GifCommand;
import one.bbn.hadder.commands.fun.MemeCommand;
import one.bbn.hadder.commands.general.*;
import one.bbn.hadder.commands.misc.*;
import one.bbn.hadder.commands.moderation.*;
import one.bbn.hadder.commands.music.*;
import one.bbn.hadder.commands.nsfw.*;
import one.bbn.hadder.commands.owner.*;
import one.bbn.hadder.commands.settings.LanguageCommand;
import one.bbn.hadder.commands.settings.UserPrefixCommand;
import one.bbn.hadder.core.CommandHandler;
import one.bbn.hadder.core.Config;
import one.bbn.hadder.db.Mongo;
import one.bbn.hadder.listener.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
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

        Mongo mongo = new Mongo(config);
        mongo.connect();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.create(GatewayIntent.getIntents(14053));

        builder.setAutoReconnect(true);
        builder.setShardsTotal(1);
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.setBulkDeleteSplittingEnabled(true);
        builder.setActivity(Activity.listening("h.help"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setToken(config.getBotToken());
        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS);

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
                        new ServerStatsCommand(),
                        new ProfileCommand(),
                        new CodeCommand(),
                        new MoveAllCommand(),
                        new CoronaCommand()), config, helpCommand);

        builder.addEventListeners(
                new MentionListener(mongo, config),
                new PrivateMessageListener(mongo),
                new CommandListener(mongo, commandHandler, audioManager),
                new GuildListener(mongo, config),
                new ReadyListener(config),
                new InviteLinkListener(mongo),
                new RulesListener(mongo),
                new StarboardListener(mongo),
                new VoiceLeaveListener(audioManager),
                new OwnerMessageListener(config));

        try {
            shardManager = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
