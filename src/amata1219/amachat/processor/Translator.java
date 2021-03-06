package amata1219.amachat.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.Validate;

import com.google.common.io.CharStreams;

public class Translator implements TextProcessor {

	private final String url;
	public final Set<LanguageCode> allowedLanguage;

	public Translator(String url, Set<LanguageCode> allowedLanguage){
		Validate.notNull(url, "URL can not be null");

		this.url = url;
		this.allowedLanguage = allowedLanguage;
	}

	@Override
	public String process(String text) {
		if(text.isEmpty() || !canProcess(text))
			return text;

		String[] splittedText = text.split(":", 1);
		if(splittedText.length != 2)
			return text;

		Optional<LanguageCode> code = LanguageCode.toLanguageCode(splittedText[0]);
		if(!code.isPresent() || !allowedLanguage.contains(code))
			return text;

		StringBuilder builder = new StringBuilder();
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			connection = (HttpURLConnection) new URL(url.replace("$2", code.get().toString()).replace("$1", URLEncoder.encode(text, "UTF-8"))).openConnection();
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(true);
			connection.connect();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

			builder.append(CharStreams.toString(reader));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(connection != null)
				connection.disconnect();

			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return builder.toString();
	}

	@Override
	public boolean canProcess(String text){
		return text.indexOf(':') == 3;
	}

	public enum LanguageCode {

		IS,//アイスランド語
		GA,//アイルランド語
		AZ,//アゼルバイジャン語
		AF,//アフリカーンス語
		AM,//アムハラ語
		AR,//アラビア語
		SQ,//アルバニア語
		HY,//アルメニア語
		IT,//イタリア語
		YI,//イディッシュ語
		IG,//イボ語
		ID,//インドネシア語
		CY,//ウェールズ語
		UK,//ウクライナ語
		UZ,//ウズベク語
		UR,//ウルドゥ語
		ET,//エストニア語
		EO,//エスペラント語
		NL,//オランダ語
		KK,//カザフ語
		CA,//カタルーニャ語
		GL,//ガリシア語
		KN,//カンナダ語
		EL,//ギリシャ語
		KY,//キルギス語
		GU,//グジャラト語
		KM,//クメール語
		KU,//クルド語
		HR,//クロアチア語
		XH,//コーサ語
		CO,//コルシカ語
		SM,//サモア語
		JV,//ジャワ語
		KA,//ジョージア語
		SN,//ショナ語
		SD,//シンド語
		SI,//シンハラ語
		SV,//スウェーデン語
		ZU,//ズールー語
		GD,//スコットランド ゲール語
		ES,//スペイン語
		SK,//スロバキア語
		SL,//スロベニア語
		SW,//スワヒリ語
		SU,//スンダ語
		//CB,//セブアノ語(CEB)
		SR,//セルビア語
		SX,//ソト語
		SO,//ソマリ語
		TH,//タイ語
		TL,//タガログ語
		TG,//タジク語
		TA,//タミル語
		CS,//チェコ語
		NY,//チェワ語
		TE,//テルグ語
		DA,//デンマーク語
		DE,//ドイツ語
		TR,//トルコ語
		NE,//ネパール語
		NO,//ノルウェー語
		HT,//ハイチ語
		HA,//ハウサ語
		PS,//パシュト語
		EU,//バスク語
		//HW,//ハワイ語(HAW)
		HU,//ハンガリー語
		PA,//パンジャブ語
		HI,//ヒンディー語
		FI,//フィンランド語
		FR,//フランス語
		FY,//フリジア語
		BG,//ブルガリア語
		VI,//ベトナム語
		HE,//ヘブライ語
		BE,//ベラルーシ語
		FA,//ペルシャ語
		BN,//ベンガル語
		PL,//ポーランド語
		BS,//ボスニア語
		PT,//ポルトガル語
		MI,//マオリ語
		MK,//マケドニア語
		MR,//マラーティー語
		MG,//マラガシ語
		ML,//マラヤーラム語
		MT,//マルタ語
		MS,//マレー語
		MY,//ミャンマー語
		MN,//モンゴル語
		//HN,//モン語(HMN)
		YO,//ヨルバ語
		LO,//ラオ語
		LA,//ラテン語
		LV,//ラトビア語
		LT,//リトアニア語
		RO,//ルーマニア語
		LB,//ルクセンブルク語
		RU,//ロシア語
		EN,//英語
		KO,//韓国語
		ZH,//中国語
		JA;//日本語

		public static Optional<LanguageCode> toLanguageCode(String text){
			LanguageCode code = null;
			try{
				code = valueOf(text.toUpperCase());
			}catch(Exception e){
				e.printStackTrace();
			}
			return Optional.of(code);
		}

	}

}
