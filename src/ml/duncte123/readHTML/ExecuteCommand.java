package ml.duncte123.readHTML;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ExecuteCommand {
	
	public ExecuteCommand(String name, String action, String[] args, MessageReceivedEvent event){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		engine.put("event", event);
		try {
			engine.eval(action);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
