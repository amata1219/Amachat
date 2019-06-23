package amata1219.amachat.bungee.config;

import java.util.Map;

import amata1219.amachat.collection.LockableHashMap;
import amata1219.amachat.collection.LockableHashMap.LockableHashMapLocker;

public class MainConfig extends Config {

	/*
	 * Servers:
	 *   Name: Alias
	 *   Name: Alias
	 *
	 * HolderPlacerEnable: true
	 * PlaceHolders:
	 *   Holder: Replacement
	 *   Holder: Replacement
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

		config.getSection("Servers").getKeys()
			.forEach(serverName -> aliases.bypass((map) -> map.put(serverName, config.getString("Servers." + serverName))));

		holderPlacerEnable = config.getBoolean("HolderPlacerEnable");

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
