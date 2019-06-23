package amata1219.amachat.processor;

import net.md_5.bungee.api.ChatColor;

public class Colorist implements TextProcessor {

	public static final Colorist INSTANCE = new Colorist();

	@Override
	public String process(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

}
