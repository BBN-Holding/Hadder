package com.bbn.hadder.commands.owner;

/*
 * @author Skidder / GregTCLTK
 */

import com.bbn.hadder.Hadder;
import com.bbn.hadder.commands.Command;
import com.bbn.hadder.commands.CommandEvent;
import com.bbn.hadder.core.Perm;
import com.bbn.hadder.core.Perms;
import com.bbn.hadder.utils.MessageEditor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Perms(Perm.BOT_OWNER)
public class EvalCommand implements Command {

    @Override
    public void executed(String[] args, CommandEvent e) {
        if (args.length > 0) {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

            try {
                engine.eval("var imports = new JavaImporter(java.io, java.lang, java.util);");
            } catch (ScriptException ex) {
                ex.printStackTrace();
            }

            engine.put("msg".toLowerCase(), e.getMessage());
            engine.put("shardmanager".toLowerCase(), Hadder.shardManager);
            engine.put("rethink".toLowerCase(), e.getRethink());
            engine.put("e".toLowerCase(), e);
            engine.put("jda".toLowerCase(), e.getJDA());
            engine.put("message".toLowerCase(), e.getMessage());
            engine.put("guild".toLowerCase(), e.getGuild());
            engine.put("channel".toLowerCase(), e.getChannel());
            engine.put("author".toLowerCase(), e.getAuthor());
            engine.put("member".toLowerCase(), e.getMember());
            engine.put("self".toLowerCase(), e.getGuild().getSelfMember());
            engine.put("audio".toLowerCase(), e.getAudioManager());
            engine.put("out".toLowerCase(), System.out);

            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

            service.schedule(() -> {

                long startExec = System.currentTimeMillis();
                Object out;

                try {
                    StringBuilder script = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        args[i] = args[i].replace("```java", "").replace("```", "");
                        script.append(i == args.length - 1 ? args[i] : args[i] + " ");
                    }
                    out = engine.eval(script.toString());

                    e.getTextChannel().sendMessage(e.getMessageEditor()
                            .getMessage(MessageEditor.MessageType.INFO, "commands.owner.eval.success.title", "")
                            .addField(e.getMessageEditor().getTerm("commands.owner.eval.success.input"),
                                    "```java\n\n" + script + "```", false)
                            .addField(e.getMessageEditor().getTerm("commands.owner.eval.success.output"),
                                    "```java\n\n" + out.toString() + "```", false)
                            .addField(e.getMessageEditor().getTerm("commands.owner.eval.success.timing"),
                                    System.currentTimeMillis() - startExec + " milliseconds", false)
                            .build()).queue();
                } catch (Exception ex) {
                    e.getTextChannel().sendMessage(e.getMessageEditor()
                            .getMessage(MessageEditor.MessageType.INFO, "commands.owner.eval.success.title", "")
                            .addField(e.getMessageEditor().getTerm("error"),
                                    "```java\n\n" + ex.getMessage() + "```", false)
                            .addField(e.getMessageEditor().getTerm("commands.owner.eval.success.timing"),
                                    System.currentTimeMillis() - startExec + " milliseconds", false)
                            .build()).queue();

                }

                service.shutdownNow();

            }, 0, TimeUnit.MILLISECONDS);

        } else {
            e.getHelpCommand().sendHelp(this, e);
        }
    }

    @Override
    public String[] labels() {
        return new String[]{"eval"};
    }

    @Override
    public String description() {
        return "commands.owner.eval.help.description";
    }

    @Override
    public String usage() {
        return "[Code]";
    }

    @Override
    public String example() {
        return "System.out.println(\"Hey\")";
    }
}
