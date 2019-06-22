package amata1219.amachat.processor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HolderPlacer implements TextProcessor {

	private final Pattern[] placeHolders;
	private final String[] replacements;

	public HolderPlacer(Map<String, String> pairs){
		placeHolders = pairs.keySet().stream()
				.map(placeHolder -> Pattern.compile(placeHolder, Pattern.LITERAL))
				.toArray(Pattern[]::new);

		replacements = pairs.values().stream()
				.map(Matcher::quoteReplacement)
				.toArray(String[]::new);
	}

	@Override
	public String process(String text) {
		for(int i = 0; i < placeHolders.length; i++)
			text = placeHolders[i].matcher(text).replaceAll(replacements[i]);
		return text;
	}

}
