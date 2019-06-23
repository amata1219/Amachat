package amata1219.amachat.bungee;

import amata1219.amachat.bungee.config.MainConfig;
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

		ProxiedPlayer player = (ProxiedPlayer) sender;


	}

}
