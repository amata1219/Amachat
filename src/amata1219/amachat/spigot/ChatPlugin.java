package amata1219.amachat.spigot;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatPlugin extends JavaPlugin {

	private static ChatPlugin plugin;

	@Override
	public void onEnable(){
		plugin = this;
	}

	@Override
	public void onDisable(){
		HandlerList.unregisterAll((ChatPlugin) this);
	}

	public static ChatPlugin getPlugin(){
		return plugin;
	}

}
