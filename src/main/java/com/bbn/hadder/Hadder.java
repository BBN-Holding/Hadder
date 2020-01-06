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

import javax.security.auth.login.LoginException;
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
        builder.setActivity(Activity.streaming("on the BigBotNetwork", "https://twitch.tv/BigBotNetwork"));
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
                        new LoopCommand()), config, helpCommand);

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
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
