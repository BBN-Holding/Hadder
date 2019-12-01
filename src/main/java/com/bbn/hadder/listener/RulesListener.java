package com.bbn.hadder.listener;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Rethink;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
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
                if (event.getReactionEmote().getEmoji().equals("✅")) {
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Accepted rules").queue();
                } else if (event.getReactionEmote().getEmoji().equals("❌") && event.getGuild().getSelfMember().canInteract(event.getMember())) {
                    event.getReaction().removeReaction().queue();
                    event.getMember().kick().reason("Declined the rules");
                }
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getMessageId().equals(rethink.getRulesMID(event.getGuild().getId()))) {
            if (!event.getMember().getUser().isBot() && event.getReactionEmote().getEmoji().equals("✅")) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Withdrawal of the acceptance of the rules").queue();
            }
        }
    }
}
