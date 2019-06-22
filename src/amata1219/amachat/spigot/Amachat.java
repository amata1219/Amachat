package amata1219.amachat.spigot;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Amachat extends JavaPlugin {

	private static Amachat plugin;

	@Override
	public void onEnable(){
		plugin = this;
	}

	@Override
	public void onDisable(){
		HandlerList.unregisterAll((Amachat) this);
	}

	public static Amachat getPlugin(){
		return plugin;
	}

}
