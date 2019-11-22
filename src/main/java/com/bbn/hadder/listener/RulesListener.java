package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RulesListener extends ListenerAdapter {

    private Rethink rethink;

    public RulesListener(Rethink rethink) {
        this.rethink = rethink;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getMessageId().equals(rethink.getRulesMID(event.getGuild().getId()))) {
            if (!event.getMember().getUser().isBot()) {
                if (event.getReactionEmote().equals("✅")) {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).queue();
                } else if (event.getReactionEmote().equals("❌")) {
                    event.getMember().kick().reason("Declined the rules").queue();
                }
            }
        }
    }
}
