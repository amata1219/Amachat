package amata1219.amachat.bungee;

import amata1219.amachat.bungee.config.MainConfig;
import amata1219.amachat.processor.Japanizer;
import amata1219.amachat.processor.Translator;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Amachat extends Plugin implements Listener {

	/*
	 * Tasks
	 *
	 * ・チャンネルチャット
	 * ・個人チャット
	 * ・メール(簡易)
	 *
	 * ----------------
	 * ・ミュート
	 * ・非表示
	 * ・ロギング
	 * ・プレイヤー名クリックで個人チャットコマンドを挿入
	 *
	 * -------
	 *
	 * onChat(Bungee) → sendMessage(Bungee) + sendPluginMessage(Bungee):Dynmap用
	 *
	 */

	private static Amachat plugin;
	private static UserManager userManager;

	private MainConfig mainConfig;

	private Translator translator;

	@Override
	public void onEnable(){
		plugin = this;

		mainConfig = new MainConfig().reload();
	}

	@Override
	public void onDisable(){

	}

	public static Amachat getPlugin(){
		return plugin;
	}

	public static UserManager getUserManager(){
		return userManager;
	}

	@EventHandler
	public void onChat(ChatEvent event){
		if(event.isCommand())
			return;

		Connection sender = event.getSender();
		if(!(sender instanceof ProxiedPlayer))
			return;

		User user = userManager.wrap((ProxiedPlayer) sender);
		String text = event.getMessage();

		//prefix判定

		Japanizer japanizer = Japanizer.INSTANCE;
		if(mainConfig.isEnableJapanizer() && user.useJapanize && japanizer.canProcess(text))
			text = japanizer.process(text);
		else if(translator != null && translator.canProcess(text))
			text = translator.process(text);

		event.setCancelled(true);
	}

}
