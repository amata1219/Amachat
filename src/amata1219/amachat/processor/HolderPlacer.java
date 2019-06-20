package amata1219.amachat.processor;

import java.util.Map;

public class HolderPlacer implements TextProcessor {

	private final Map<String, String> holders;

	public HolderPlacer(Map<String, String> holders){
		this.holders = holders;
	}

	@Override
	public String process(String text) {
		for(String holder : holders.keySet())
			text = text.replace(holder, holders.get(holder));
		return text;
	}

}
