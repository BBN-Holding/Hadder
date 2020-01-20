package com.bbn.hadder.listener;

import com.bbn.hadder.Rethink;
import com.bbn.hadder.RethinkServer;
import com.bbn.hadder.RethinkUser;
import com.bbn.hadder.audio.AudioManager;
import com.bbn.hadder.core.CommandHandler;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;

/*
 * @author Skidder / GregTCLTK
 */

public class CommandListener extends ListenerAdapter {

    private Rethink rethink;
    private CommandHandler handler;
    private AudioManager audioManager;

    public CommandListener(Rethink rethink, CommandHandler handler, AudioManager audioManager) {
        this.rethink = rethink;
        this.handler = handler;
        this.audioManager = audioManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().isBot()) {
            RethinkUser rethinkUser = new RethinkUser(rethink.getObjectByID("user", event.getAuthor().getId()), rethink);
            RethinkServer rethinkServer = new RethinkServer(rethink.getObjectByID("server", event.getGuild().getId()), rethink);
            rethinkUser.push();
            rethinkServer.push();
            String[] prefixes = {
                    rethinkUser.getPrefix(), rethinkServer.getPrefix(),
                    event.getGuild().getSelfMember().getAsMention() + " ", event.getGuild().getSelfMember().getAsMention(),
                    event.getGuild().getSelfMember().getAsMention().replace("@", "@!") + " ",
                    event.getGuild().getSelfMember().getAsMention().replace("@", "@!")
            };
            for (String prefix : prefixes) {
                if (event.getMessage().getContentRaw().startsWith(prefix)) {
                    handler.handle(event, rethink, prefix, audioManager, rethinkUser, rethinkServer);
                    return;
                }
            }
        }
    }
}
