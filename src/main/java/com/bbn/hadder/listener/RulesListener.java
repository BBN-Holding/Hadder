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
        if (event.getMessageId().equals(rethink.getRulesMID(event.getGuild().getId())) && !event.getMember().getUser().isBot()) {
            if (event.getReactionEmote().isEmote()) {
                if (rethink.getRulesAEmote(event.getGuild().getId()).equals(event.getReactionEmote().getId())) {
                    addRole(event);
                } else if (rethink.getRulesDEmote(event.getGuild().getId()).equals(event.getReactionEmote().getId())) {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if (event.getGuild().getSelfMember().canInteract(event.getMember())) {
                        event.getMember().kick().reason("Declined the rules");
                    }
                }
            } else if (event.getReactionEmote().isEmoji()) {
                if (rethink.getRulesAEmote(event.getGuild().getId()).equals(event.getReactionEmote().getEmoji())) {
                    addRole(event);
                } else if (rethink.getRulesDEmote(event.getGuild().getId()).equals(event.getReactionEmote().getEmoji())) {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if (event.getGuild().getSelfMember().canInteract(event.getMember())) {
                        event.getMember().kick().reason("Declined the rules");
                    }
                }
            }
        }
    }

    private void addRole(MessageReactionAddEvent event) {
        if (event.getMember().getRoles().contains(event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId())))) {
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Accepted rules").queue();
        } else event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Accepted rules").queue();
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getMessageId().equals(rethink.getRulesMID(event.getGuild().getId())) && !event.getMember().getUser().isBot()) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Withdrawal of the acceptance of the rules").queue();
        }
    }
}
