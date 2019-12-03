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
            System.out.println("Debug -1");
            if (event.getReactionEmote().isEmote()) {
                System.out.println("Debug 0");
                if (rethink.getRulesAEmote(event.getGuild().getId()).contains(event.getReactionEmote().getId())) {
                    System.out.println("Debug 1");
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Accepted rules").queue();
                } else if (rethink.getRulesDEmote(event.getGuild().getId()).contains(event.getReactionEmote().getId())) {
                    System.out.println("Debug 2");
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if (event.getGuild().getSelfMember().canInteract(event.getMember())) {
                        event.getMember().kick().reason("Declined the rules");
                    }
                } else {
                    System.out.println("Debug 3");
                }
            } else {
                System.out.println("Debug 4");
                if (event.getReactionEmote().getEmoji().equals(rethink.getRulesAEmote(event.getGuild().getId()))) {
                    System.out.println("Debug 5");
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Accepted rules").queue();
                } else if (event.getReactionEmote().getEmoji().equals(rethink.getRulesDEmote(event.getGuild().getId()))) {
                    System.out.println("Debug 6");
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if (event.getGuild().getSelfMember().canInteract(event.getMember())) {
                        event.getMember().kick().reason("Declined the rules");
                    }
                } else {
                    System.out.println("Debug 7");
                }
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getMessageId().equals(rethink.getRulesMID(event.getGuild().getId())) && !event.getMember().getUser().isBot()) {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(rethink.getRulesRID(event.getGuild().getId()))).reason("Withdrawal of the acceptance of the rules").queue();
        }
    }
}
