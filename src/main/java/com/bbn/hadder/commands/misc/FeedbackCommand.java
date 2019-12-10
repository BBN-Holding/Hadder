package com.bbn.hadder.commands.misc;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.EventWaiter;
import com.bbn.hadder.utils.MessageEditor;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class FeedbackCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.misc.feedback.title.request.title"))
                .setDescription(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.misc.feedback.title.request.description"))
                .build()).queue();
        new EventWaiter().newOnMessageEventWaiter(event1 -> {
                String title = event1.getMessage().getContentDisplay();
                event1.getChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.misc.feedback.description.request.title"))
                        .setTitle(MessageEditor.handle(event.getRethink().getLanguage(event.getAuthor().getId()), "commands.misc.feedback.description.request.description"))
                        .build()).queue();
            new EventWaiter().newOnMessageEventWaiter(event2 -> {
                String description = event2.getMessage().getContentDisplay();
                try {
                    GitHub connection = GitHub.connectUsingOAuth(event.getConfig().getGitHubToken());
                    GHRepository Hadder = connection.getOrganization("BigBotNetwork").getRepository("Hadder");
                    GHIssue issue = Hadder.createIssue(title).body("<strong>Feedback by " + event.getAuthor().getAsTag() + "</strong><br>" + description).label("feedback").create();
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO)
                            .setTitle("Feedback successfully sent")
                            .setDescription(issue.getHtmlUrl().toString())
                            .build()).queue();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }, event.getJDA(), event.getAuthor());
            }, event.getJDA(), event.getAuthor());

    }

    @Override
    public String[] labels() {
        return new String[]{"feedback"};
    }

    @Override
    public String description() {
        return MessageEditor.handle("en", "commands.misc.feedback.help.description");
    }

    @Override
    public String usage() {
        return "";
    }
}
