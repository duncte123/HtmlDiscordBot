package ml.duncte123.readHTML;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.utils.SimpleLog;

public class HtmlBot {
	
	private static JDA jda;
	
	public static final CommandParser parser = new CommandParser();
	public static HashMap<String, Command> commands = new HashMap<String, Command>();
	
	static String prefix;
	static String game;
	static final String defaultName = "duncte's bot";
	static String logName = defaultName;
	private static SimpleLog logger2 = SimpleLog.getLog(logName);

	public static void main(String[] args) throws IOException {
		
		Document doc = Jsoup.connect("http://example.com/bot.php?token=YOUR_BOT_TOKEN").get();
		
		game = doc.getElementsByTag("title").first().text();
		String token = doc.getElementsByTag("token").first().text();
		prefix = doc.getElementsByTag("prefix").first().text();
		Elements commands = doc.getElementsByTag("commands").first().children();
		
		//System.out.println("Game is: " + game);
		//System.out.println("Token is: " + token);
		//System.out.println("Prefix is: " + prefix);
		//System.out.println("Commands are: ");
		String invoke = "";
		String action = "";
		for(Element command_: commands){
			Elements cmds = command_.getElementsByTag("name");
			
			for(Element cmdName: cmds){
				String name = cmdName.text();
				if(!name.equals("")){
					invoke = name;
					//System.out.println("	Command name: " + name.toString());
				}
			}
			
			Elements actions = command_.getElementsByTag("script");
			for(Element action_: actions){
				String Action = action_.html();
				if(!Action.equals("")){
					action = Action;
				}
			}
			//System.out.println("	Action is: " + action);
			if(!invoke.equals("") && !action.equals("")){
				//System.out.println("	Action 2 is: " + action);
				//System.out.println("	Invoke 2 is: " + invoke);
				HtmlBot.commands.put(invoke, new Command(invoke, action.trim()));
				invoke = "";
				action = "";
			}
		}
		
		// log in
		try{
			jda = new JDABuilder(AccountType.BOT)
					.setBulkDeleteSplittingEnabled(false)
					.setAudioEnabled(false)
					.addEventListener(new BotListener())
					.setToken(token)
					.buildBlocking();
			jda.setAutoReconnect(true);
			jda.getPresence().setStatus(OnlineStatus.ONLINE);
			jda.getPresence().setGame(Game.of(game, "https://twitch.tv/duncte123"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final void log(String name, SimpleLog.Level lvl, String message){
		logName = name;
		logger2.log(lvl, message);
		
	}
	
	public static final void log(SimpleLog.Level lvl, String message){
		log(defaultName, lvl, message);
	}
	
	public static void handleCommand(CommandParser.CommandContainer cmd){
		if(commands.containsKey(cmd.invoke)){
			new ExecuteCommand(cmd.invoke, commands.get(cmd.invoke).action, cmd.args, cmd.event);
		}
	}
}
