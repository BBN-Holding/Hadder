package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.utils.MessageEditor;
import net.dv8tion.jda.api.EmbedBuilder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EvalCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent event) {
        if (event.getConfig().getOwners().toString().contains(event.getAuthor().getId())) {
            if (args.length > 0) {
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

                try {
                    engine.eval("var imports = new JavaImporter(java.io, java.lang, java.util);");
                } catch (ScriptException ex) {
                    ex.printStackTrace();
                }

                engine.put("event", event);
                engine.put("jda", event.getJDA());
                engine.put("message", event.getMessage());
                engine.put("guild", event.getGuild());
                engine.put("channel", event.getChannel());
                engine.put("author", event.getAuthor());
                engine.put("member", event.getMember());
                engine.put("self", event.getGuild().getSelfMember());

                ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

                ScheduledFuture<?> future = service.schedule(() -> {

                    long startExec = System.currentTimeMillis();
                    Object out = null;
                    EmbedBuilder builder = new EmbedBuilder();

                    try {
                        String script = "";
                        for (int i = 0; i < args.length; i++) {
                            args[i] = args[i].replace("```java", "").replace("```", "");
                            script += i == args.length-1 ? args[i]:args[i]+" ";
                        }
                        builder.addField("Input", "```java\n\n" + script + "```", false);

                        out = engine.eval(script);
                        builder.addField("Output", "```java\n\n" + out.toString() + "```", false);

                    } catch (Exception ex) {
                        builder.addField("Error", "```java\n\n" + ex.getMessage() + "```", false);
                    }

                    builder.addField("Timing", System.currentTimeMillis()-startExec + " milliseconds", false);
                    event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.INFO, builder).setTitle("Eval Command").build()).queue();

                    service.shutdownNow();

                }, 0, TimeUnit.MILLISECONDS);

            } else {
                event.getHelpCommand().sendHelp(this, event.getRethink(), event.getAuthor(), event.getTextChannel());
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            event.getTextChannel().sendMessage(new MessageEditor().setDefaultSettings(MessageEditor.MessageType.NO_PERMISSION, builder).build()).queue();
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"eval"};
    }

    @Override
    public String description() {
        return "You know what a eval command is ;)";
    }

    @Override
    public String usage() {
        return "<Code to execute>";
    }
}
