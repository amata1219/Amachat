package amata1219.amachat.bungee.config;

import java.util.Map;

import amata1219.amachat.collection.LockableHashMap;
import amata1219.amachat.collection.LockableHashMap.LockableHashMapLocker;
import amata1219.amachat.processor.Colorist;

public class MainConfig extends Config {

	/*
	 * Servers:
	 *   Name: Alias
	 *   Name: Alias
	 *
	 * HolderPlacerEnable: true
	 * PlaceHolders:
	 * - "::"
	 * - ""
	 *
	 * JapanizerEnable: true
	 * TranslatorEnable: true
	 *
	 */

	private LockableHashMapLocker<String, String> aliases = LockableHashMap.of();
	private boolean holderPlacerEnable;
	private LockableHashMapLocker<String, String> placeHolders = LockableHashMap.of();
	private boolean japanizerEnable;
	private boolean translatorEnable;

	public MainConfig(){
		super("config.yml");
	}

	@Override
	public MainConfig reload(){
		super.reload();

		Colorist colorist = Colorist.INSTANCE;

		config.getSection("Servers").getKeys()
			.forEach(serverName -> aliases.bypass((map) -> map.put(serverName, colorist.process(config.getString("Servers." + serverName)))));

		holderPlacerEnable = config.getBoolean("HolderPlacerEnable");

		config.getStringList("PlaceHolders").stream()
			.map(text -> colorist.process(text).split("::"))
			.forEach(splittedText -> placeHolders.bypass((map) -> map.put(splittedText[0], splittedText[1])));

		config.getSection("PlaceHolders").getKeys()
			.forEach(holder -> placeHolders.bypass((map) -> map.put(holder, config.getString("PlaceHolders." + holder))));

		japanizerEnable = config.getBoolean("JapanizerEnable");
		translatorEnable = config.getBoolean("TranslatorEnable");

		return this;
	}

	public Map<String, String> getAliases(){
		return aliases.map;
	}

	public boolean isEnableHolderPlacer(){
		return holderPlacerEnable;
	}

	public Map<String, String> getPlaceHolders(){
		return placeHolders.map;
	}

	public boolean isEnableJapanizer(){
		return japanizerEnable;
	}

	public boolean isEnableTranslator(){
		return translatorEnable;
	}

}
