package amata1219.amachat.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public final class Japanizer implements TextProcessor {

	public static final Japanizer INSTANCE = new Japanizer();

	private static final String CHARACTER_ENCODING = "UTF-8";

	private static final Pattern[] ROMAN_LETTER_ARRAY;
	private static final String[] HIRAGANA_ARRAY;

	public static void main(String[] args){
		Japanizer japanizer = new Japanizer();
		System.out.println(japanizer.process("konbanha"));
		System.out.println(japanizer.process("kombanha"));
	}

	static{
		ImmutableMap<String, String> correspondenceTable = CorrespondenceTableBuilder.build(
			"yi", "い",
			"wu", "う",
			"whu", "う",
			"wha", "うぁ",
			"whi", "うぃ",
			"wi", "うぃ",
			"whe", "うぇ",
			"we", "うぇ",
			"who", "うぉ",
			"la", "ぁ",
			"xa", "ぁ",
			"li", "ぃ",
			"xi", "ぃ",
			"lyi", "ぃ",
			"xyi", "ぃ",
			"lu", "ぅ",
			"xu", "ぅ",
			"le", "ぇ",
			"xe", "ぇ",
			"lye", "ぇ",
			"xye", "ぇ",
			"lo", "ぉ",
			"xo", "ぉ",
			"ye", "いぇ",
			"ka", "か",
			"ca", "か",
			"ki", "き",
			"ku", "く",
			"cu", "く",
			"qu", "く",
			"ke", "け",
			"ko", "こ",
			"co", "こ",
			"kya", "きゃ",
			"kyi", "きぃ",
			"kyu", "きゅ",
			"kye", "きぇ",
			"kyo", "きょ",
			//"qya", "くゃ",
			//"qyu", "くゅ",
			//"qyo", "くょ",
			//"qwa", "くぁ",
			"qa", "くぁ",
			"kwa", "くぁ",
			"qwi", "くぃ",
			"qi", "くぃ",
			//"qyi", "くぃ",
			//"qwu", "くぅ",
			//"qwe", "くぇ",
			"qe", "くぇ",
			//"qye", "くぇ",
			//"qwo", "くぉ",
			"qo", "くぉ",
			"ga", "が",
			"gi", "ぎ",
			"gu", "ぐ",
			"ge", "げ",
			"go", "ご",
			"gya", "ぎゃ",
			"gyi", "ぎぃ",
			"gyu", "ぎゅ",
			"gye", "ぎぇ",
			"gyo", "ぎょ",
			"gwa", "ぐぁ",
			"gwi", "ぐぃ",
			"gwu", "ぐぅ",
			"gwe", "ぐぇ",
			"gwo", "ぐぉ",
			"lka", "ヵ",
			"xka", "ヵ",
			"lke", "ヶ",
			"xke", "ヶ",
			"sa", "さ",
			"si", "し",
			"ci", "し",
			"shi", "し",
			"su", "す",
			"se", "せ",
			"ce", "せ",
			"so", "そ",
			"sya", "しゃ",
			"sha", "しゃ",
			"syi", "しぃ",
			"syu", "しゅ",
			"shu", "しゅ",
			"sye", "しぇ",
			"she", "しぇ",
			"syo", "しょ",
			"sho", "しょ",
			//"swa", "すぁ",
			//"swi", "すぃ",
			"swu", "すぅ",
			"swe", "すぇ",
			"swo", "すぉ",
			"za", "ざ",
			"zi", "じ",
			"ji", "じ",
			"zu", "ず",
			"ze", "ぜ",
			"zo", "ぞ",
			"zya", "じゃ",
			"ja", "じゃ",
			"jya", "じゃ",
			"zyi", "じぃ",
			"jyi", "じぃ",
			"zyu", "じゅ",
			"ju", "じゅ",
			"jyu", "じゅ",
			"zye", "じぇ",
			"je", "じぇ",
			"jye", "じぇ",
			"zyo", "じょ",
			"jo", "じょ",
			"jyo", "じょ",
			"ta", "た",
			"ti", "ち",
			"chi", "ち",
			"tu", "つ",
			"tsu", "つ",
			"te", "て",
			"to", "と",
			"tya", "ちゃ",
			"cha", "ちゃ",
			"cya", "ちゃ",
			"tyi", "ちぃ",
			"cyi", "ちぃ",
			"tyu", "ちゅ",
			"chu", "ちゅ",
			"cyu", "ちゅ",
			"tye", "ちぇ",
			"che", "ちぇ",
			"cye", "ちぇ",
			"tyo", "ちょ",
			"cho", "ちょ",
			"cyo", "ちょ",
			"tsa", "つぁ",
			"tsi", "つぃ",
			"tse", "つぇ",
			"tso", "つぉ",
			"tha", "てゃ",
			"thi", "てぃ",
			"thu", "てゅ",
			"the", "てぇ",
			"tho", "てょ",
			"twa", "とぁ",
			"twi", "とぃ",
			"twu", "とぅ",
			"twe", "とぇ",
			"two", "とぉ",
			"da", "だ",
			"di", "ぢ",
			"du", "づ",
			"de", "で",
			"do", "ど",
			"dya", "ぢゃ",
			"dyi", "ぢぃ",
			"dyu", "ぢゅ",
			"dye", "ぢぇ",
			"dyo", "ぢょ",
			"dha", "でゃ",
			"dhi", "でぃ",
			"dhu", "でゅ",
			"dhe", "でぇ",
			"dho", "でょ",
			"dwa", "どぁ",
			"dwi", "どぃ",
			"dwu", "どぅ",
			"dwe", "どぇ",
			"dwo", "どぉ",
			"ltu", "っ",
			"xtu", "っ",
			"ltsu", "っ",
			"na", "な",
			"ni", "に",
			"nu", "ぬ",
			"ne", "ね",
			"no", "の",
			"nya", "にゃ",
			"nyi", "にぃ",
			"nyu", "にゅ",
			"nye", "にぇ",
			"nyo", "にょ",
			"ha", "は",
			"hi", "ひ",
			"hu", "ふ",
			"fu", "ふ",
			"he", "へ",
			"ho", "ほ",
			"hya", "ひゃ",
			"hyi", "ひぃ",
			"hyu", "ひゅ",
			"hye", "ひぇ",
			"hyo", "ひょ",
			//"fwa", "ふぁ",
			"fa", "ふぁ",
			//"fwi", "ふぃ",
			"fi", "ふぃ",
			//"fyi", "ふぃ",
			//"fwu", "ふぅ",
			//"fwe", "ふぇ",
			"fe", "ふぇ",
			//"fye", "ふぇ",
			//"fwo", "ふぉ",
			"fo", "ふぉ",
			"fya", "ふゃ",
			"fyu", "ふゅ",
			"fyo", "ふょ",
			"ba", "ば",
			"bi", "び",
			"bu", "ぶ",
			"be", "べ",
			"bo", "ぼ",
			"bya", "びゃ",
			"byi", "びぃ",
			"byu", "びゅ",
			"bye", "びぇ",
			"byo", "びょ",
			"va", "ゔぁ",
			"vi", "ゔぃ",
			"vu", "ゔ",
			"ve", "ゔぇ",
			"vo", "ゔぉ",
			"vya", "ゔゃ",
			"vyi", "ゔぃ",
			"vyu", "ゔゅ",
			"vye", "ゔぇ",
			"vyo", "ゔょ",
			"pa", "ぱ",
			"pi", "ぴ",
			"pu", "ぷ",
			"pe", "ぺ",
			"po", "ぽ",
			"pya", "ぴゃ",
			"pyi", "ぴぃ",
			"pyu", "ぴゅ",
			"pye", "ぴぇ",
			"pyo", "ぴょ",
			"ma", "ま",
			"mi", "み",
			"mu", "む",
			"me", "め",
			"mo", "も",
			"mya", "みゃ",
			"myi", "みぃ",
			"myu", "みゅ",
			"mye", "みぇ",
			"myo", "みょ",
			"ya", "や",
			"yu", "ゆ",
			"yo", "よ",
			"lya", "ゃ",
			"xya", "ゃ",
			"lyu", "ゅ",
			"xyu", "ゅ",
			"lyo", "ょ",
			"xyo", "ょ",
			"ra", "ら",
			"ri", "り",
			"ru", "る",
			"re", "れ",
			"ro", "ろ",
			"rya", "りゃ",
			"ryi", "りぃ",
			"ryu", "りゅ",
			"rye", "りぇ",
			"ryo", "りょ",
			"wa", "わ",
			"wo", "を",
			"lwa", "ゎ",
			"xwa", "ゎ",

			"nn", "ん",
			"xn", "ん",

			"a", "あ",
			"i", "い",
			"u", "う",
			"e", "え",
			"o", "お",
			"n", "ん",

			",", "、",
			".", "。",
			"-", "ー",
			"!", "！",
			"?", "？",
			"[", "「",
			"]", "」"
		);

		ArrayList<String> romanLetters = new ArrayList<>(correspondenceTable.keySet());
		ArrayList<String> hiraganaList = new ArrayList<>(correspondenceTable.values());

		for(Entry<String, String> pair : correspondenceTable.entrySet()){
			String romanLetter = pair.getKey();
			String hiragana = pair.getValue();
			char firstLetter = romanLetter.charAt(0);
			if("aiueon,.-!?[]".indexOf(firstLetter) <= -1){
				romanLetters.add(0, firstLetter + romanLetter);
				hiraganaList.add(0, "っ" + hiragana);
			}

			if("bp".indexOf(firstLetter) > -1){
				romanLetters.add(0, "m" + romanLetter);
				hiraganaList.add(0, "ん" + hiragana);
			}
		}

		ROMAN_LETTER_ARRAY = romanLetters.stream()
				.map(romanLetter -> Pattern.compile(romanLetter, Pattern.LITERAL))
				.toArray(Pattern[]::new);

		HIRAGANA_ARRAY = hiraganaList.stream()
				.map(Matcher::quoteReplacement)
				.toArray(String[]::new);
	}

	@Override
	public String process(String text) {
		if(text.isEmpty())
			return "";

		for(int i = 0; i < ROMAN_LETTER_ARRAY.length; i++)
			text = ROMAN_LETTER_ARRAY[i].matcher(text).replaceAll(HIRAGANA_ARRAY[i]);

		StringBuilder builder = new StringBuilder();
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try{
			URL url = new URL("https://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + URLEncoder.encode(text, CHARACTER_ENCODING));

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(false);
			connection.connect();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARACTER_ENCODING));

			new Gson().fromJson(CharStreams.toString(reader), JsonArray.class)
				.forEach(element -> builder.append(element.getAsJsonArray().get(1).getAsJsonArray().get(0).getAsString()));
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(ProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(connection != null)
				connection.disconnect();

			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
		}

		return builder.toString();
	}

	@Override
	public boolean canProcess(String text){
		return text.length() == text.getBytes().length || text.matches("[ \\uFF61-\\uFF9F]+");
	}

	private static final class CorrespondenceTableBuilder {

		public static ImmutableMap<String, String> build(String... pairs){
			if(pairs.length % 2 != 0)
				throw new IllegalArgumentException("Arguments length must be even");

			ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

			for(int i = 0; i < pairs.length; i += 2)
				builder.put(pairs[i], pairs[i + 1]);

			return builder.build();
		}

	}

}
