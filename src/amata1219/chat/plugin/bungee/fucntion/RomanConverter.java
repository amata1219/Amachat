package amata1219.chat.plugin.bungee.fucntion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.common.collect.ImmutableMap;

public class RomanConverter implements TextProcessor {

	//http://www.google.com/transliterate?langpair=ja-Hira|ja&text=

	private final ImmutableMap<String, String[]> table = new ImmutableMapBuilder()
			.put("", "あ","い","う","え","お")
			.put("k", "か","き","く","け","こ")
			.put("s", "さ","し","す","せ","そ")
			.put("t", "た","ち","つ","て","と")
			.put("n", "な","に","ぬ","ね","の")
			.put("h", "は","ひ","ふ","へ","ほ")
			.put("m", "ま","み","む","め","も")
			.put("y", "や","い","ゆ","いぇ","よ")
			.put("r", "ら","り","る","れ","ろ")
			.put("w", "わ","うぃ","う","うぇ","を")
			.put("g", "が","ぎ","ぐ","げ","ご")
			.put("z", "ざ","じ","ず","ぜ","ぞ")
			.put("j", "じゃ","じ","じゅ","じぇ","じょ")
			.put("d", "だ","ぢ","づ","で","ど")
			.put("b", "ば","び","ぶ","べ","ぼ")
			.put("p", "ぱ","ぴ","ぷ","ぺ","ぽ")
			.put("gy", "ぎゃ","ぎぃ","ぎゅ","ぎぇ","ぎょ")
			.put("gw", "ぐぁ","ぐぃ","ぐぅ","ぐぇ","ぐぉ")
			.put("zy", "じゃ","じぃ","じゅ","じぇ","じょ")
			.put("jy", "じゃ","じぃ","じゅ","じぇ","じょ")
			.put("dy", "ぢゃ","ぢぃ","ぢゅ","ぢぇ","ぢょ")
			.put("dh", "でゃ","でぃ","でゅ","でぇ","でょ")
			.put("dw", "どぁ","どぃ","どぅ","どぇ","どぉ")
			.put("by", "びゃ","びぃ","びゅ","びぇ","びょ")
			.put("py", "ぴゃ","ぴぃ","ぴゅ","ぴぇ","ぴょ")
			.put("v", "ヴぁ","ヴぃ","ヴ","ヴぇ","ヴぉ")
			.put("vy", "ヴゃ","ヴぃ","ヴゅ","ヴぇ","ヴょ")
			.put("sh", "しゃ","し","しゅ","しぇ","しょ")
			.put("sy", "しゃ","し","しゅ","しぇ","しょ")
			.put("c", "か","し","く","せ","こ")
			.put("ch", "ちゃ","ち","ちゅ","ちぇ","ちょ")
			.put("cy", "ちゃ","ち","ちゅ","ちぇ","ちょ")
			.put("f", "ふぁ","ふぃ","ふ","ふぇ","ふぉ")
			.put("fy", "ふゃ","ふぃ","ふゅ","ふぇ","ふょ")
			.put("fw", "ふぁ","ふぃ","ふ","ふぇ","ふぉ")
			.put("q", "くぁ","くぃ","く","くぇ","くぉ")
			.put("ky", "きゃ","きぃ","きゅ","きぇ","きょ")
			.put("kw", "くぁ","くぃ","く","くぇ","くぉ")
			.put("ty", "ちゃ","ちぃ","ちゅ","ちぇ","ちょ")
			.put("ts", "つぁ","つぃ","つ","つぇ","つぉ")
			.put("th", "てゃ","てぃ","てゅ","てぇ","てょ")
			.put("tw", "とぁ","とぃ","とぅ","とぇ","とぉ")
			.put("ny", "にゃ","にぃ","にゅ","にぇ","にょ")
			.put("hy", "ひゃ","ひぃ","ひゅ","ひぇ","ひょ")
			.put("my", "みゃ","みぃ","みゅ","みぇ","みょ")
			.put("ry", "りゃ","りぃ","りゅ","りぇ","りょ")
			.put("l", "ぁ","ぃ","ぅ","ぇ","ぉ")
			.put("x", "ぁ","ぃ","ぅ","ぇ","ぉ")
			.put("ly", "ゃ","ぃ","ゅ","ぇ","ょ")
			.put("lt", "た","ち","っ","て","と")
			.put("lk", "ヵ","き","く","ヶ","こ")
			.put("xy", "ゃ","ぃ","ゅ","ぇ","ょ")
			.put("xt", "た","ち","っ","て","と")
			.put("xk", "ヵ","き","く","ヶ","こ")
			.put("wy", "わ","ゐ","う","ゑ","を")
			.put("wh", "うぁ","うぃ","う","うぇ","うぉ")
			.builder.build();

	@Override
	public String process(String text){
		return japanize(convertFromRoman(text));
	}

	public String convertFromRoman(String text){
		String line = "";
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < text.length(); i++){
			char character = text.charAt(i);
			switch(character){
			case 'a':
				builder.append(getHiragana(line, 0));
				line = "";
				break;
			case 'i':
				builder.append(getHiragana(line, 1));
				line = "";
				break;
			case 'u':
				builder.append(getHiragana(line, 2));
				line = "";
				break;
			case 'e':
				builder.append(getHiragana(line, 3));
				line = "";
				break;
			case 'o':
				builder.append(getHiragana(line, 4));
				line = "";
				break;
			default:
				if(line.equals("n") && !line.equals("y")){
					builder.append("ん");
					line = "";

					if(character == 'n')
						continue;
				}

				if(Character.isLetter(character)){
					if(Character.isUpperCase(character)){
						builder.append(line + character);
						line = "";
					}else if(line.length() == 1 && line.charAt(0) == character){
						builder.append("っ");
						line = String.valueOf(character);
					}else{
						line += character;
					}
				}else{
					switch(character){
					case ',':
						builder.append(line + "、");
						break;
					case '.':
						builder.append(line + "。");
						break;
					case '-':
						builder.append(line + "ー");
						break;
					case '!':
						builder.append(line + "！");
						break;
					case '?':
						builder.append(line + "？");
						break;
					case '[':
						builder.append(line + "「");
						break;
					case ']':
						builder.append(line + "」");
						break;
					default:
						break;
					}
					line = "";
				}
				break;
			}
		}

		return builder.append(line).toString();
	}

	public String japanize(String text){
		if(text.isEmpty())
			return "";

		StringBuilder builder = new StringBuilder();

		HttpURLConnection connection = null;
		try{
			URL url = new URL("http://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + URLEncoder.encode(text, "UTF-8"));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(false);
			connection.connect();
			//[["あたらしいあさ",["新しい朝","あたらしい朝","あたらしいあさ","アタラシイアサ","ｱﾀﾗｼｲｱｻ"]]]
			for(String line : LineReader.readAll(new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))){
				StringBuilder connector = new StringBuilder();
				int tmp = 0, index = 0;
				while(index < line.length()){
					int start = 0, end = 0;
					if(tmp < 3){
						start = line.indexOf("[", index);
						end = line.indexOf("]", index);
						if(start == -1)
							break;

						if(start < end){
							tmp++;
							index = start + 1;
						}else{
							tmp--;
							index = end + 1;
						}
					}else{
						start = line.indexOf("\"", index);
						end = line.indexOf("\"", start + 1);
						if(start == -1 || end == -1)
							break;

						connector.append(line.substring(start, end + 1));
						int indexOfBracket = line.indexOf("[", end);
						if(indexOfBracket == -1)
							break;

						tmp--;
						index = indexOfBracket + 1;
					}
				}
				builder.append(connector.toString());
			}
		}catch(MalformedURLException | ProtocolException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}


	/*
	 * japanize
	 * - ローマ字変換
	 * - GoogleIMEへ
	 */

	public static boolean canJapanize(String text){
		return text.length() == text.getBytes().length || text.matches("[ \\uFF61-\\uFF9F]+");
	}

	private String getHiragana(String consonant, int index){
		return table.containsKey(consonant) ? table.get(consonant)[index] : consonant + table.get("")[index];
	}

}
