package ml.duncte123.readHTML;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

public class BotListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event){

		if(event.isFromType(ChannelType.PRIVATE) && !event.getJDA().getSelfUser().getId().equals(event.getAuthor().getId()) ){
			HtmlBot.log(SimpleLog.Level.WARNING, "User "+event.getMessage().getAuthor().getName()+"#"+event.getMessage().getAuthor().getDiscriminator()+", tried to do something in the pm-channel.\nThe message is " + event.getMessage().getContent());
			return;
		}
		if(event.isFromType(ChannelType.PRIVATE)){
			// NO JUST NO, RETURN THAT SHIT
			return;
		}
		
		if(event.getMessage().getContent().startsWith(HtmlBot.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()){
			HtmlBot.handleCommand(HtmlBot.parser.parse(event.getMessage().getContent(), event));
			HtmlBot.log(HtmlBot.defaultName+"Command", SimpleLog.Level.INFO, "User "+event.getMessage().getAuthor().getName()+"#"+event.getMessage().getAuthor().getDiscriminator()+" ran command "+ event.getMessage().getContent().toLowerCase().split(" ")[0]);
			return;
		}
		HtmlBot.log(HtmlBot.defaultName+"Message", SimpleLog.Level.INFO, "Message from user "+event.getMessage().getAuthor().getName()+"#"+event.getMessage().getAuthor().getDiscriminator()+": "+ event.getMessage().getContent());
	}
	
	@Override
	public void onReady(ReadyEvent event){
		HtmlBot.log(SimpleLog.Level.INFO, "Logged in as " + event.getJDA().getSelfUser().getName());
		//event.getJDA().getGuilds().get(0).getPublicChannel().sendMessage(Main.defaultName+" V" + Config.version +" has been restarted.").queue();
		
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		TextChannel t = event.getGuild().getPublicChannel();
		String msg = "Welcome " + event.getMember().getAsMention() + ", to " + event.getGuild().getName() + ".";
		t.sendMessage(msg).queue();
	}
	
}
