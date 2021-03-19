/*
 * Copyright 2019-2021 GregTCLTK and Schlauer-Hax
 *
 * Licensed under the GNU Affero General Public License, Version 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.gnu.org/licenses/agpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package one.bbn.hadder.commands.owner;

import one.bbn.hadder.Hadder;
import one.bbn.hadder.commands.Command;
import one.bbn.hadder.commands.CommandEvent;
import one.bbn.hadder.core.Perm;
import one.bbn.hadder.core.Perms;
import one.bbn.hadder.utils.MessageEditor;

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

        } else e.getHelpCommand().sendHelp(this, e);
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
        return "[code]";
    }

    @Override
    public String example() {
        return "System.out.println(\"Hey\")";
    }
}
