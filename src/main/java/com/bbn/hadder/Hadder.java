package com.bbn.hadder;

import com.bbn.hadder.commands.general.*;
import com.bbn.hadder.commands.misc.*;
import com.bbn.hadder.commands.moderation.*;
import com.bbn.hadder.commands.nsfw.*;
import com.bbn.hadder.commands.owner.*;
import com.bbn.hadder.commands.fun.*;
import com.bbn.hadder.commands.settings.*;
import com.bbn.hadder.core.*;
import com.bbn.hadder.listener.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.List;

public class Hadder {

    public static  ShardManager shardManager;

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

        builder.setShardsTotal(1);
        builder.setActivity(Activity.streaming("on the BigBotNetwork", "https://twitch.tv/BigBotNetwork"));
        builder.setToken(config.getBotToken());

        HelpCommand helpCommand = new HelpCommand();

        CommandHandler commandHandler = new CommandHandler(
                List.of(
                        helpCommand,
                        new TestCommand(),
                        new BanCommand(),
                        new PrefixCommand(),
                        new ShutdownCommand(),
                        new KickCommand(),
                        new PingCommand(),
                        new GifCommand(),
                        new ClearCommand(),
                        new GitHubCommand(),
                        new ScreenShareCommand(),
                        new RebootCommand(),
                        new EqualsCommand(),
                        new InviteCommand(),
                        new NickCommand(),
                        new GuildPrefixCommand(),
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
                        new LinkCommand()), config, helpCommand);

        builder.addEventListeners(
                new MentionListener(rethink),
                new PrivateMessageListener(),
                new CommandListener(rethink, commandHandler),
                new GuildListener(rethink, config),
                new ReadyListener(rethink, config),
                new LinkListener(rethink),
                new RulesListener(rethink));


        try {
            shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
