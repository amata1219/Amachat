package amata1219.amachat.bungee.config;

public class ChatDataConfig extends Config {

	public ChatDataConfig(){
		super("chat_data.yml");
	}

	@Override
	public ChatDataConfig reload(){
		super.reload();

		return this;
	}

}
