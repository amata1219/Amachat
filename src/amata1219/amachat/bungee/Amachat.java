package amata1219.amachat.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class Amachat extends Plugin {

	/*
	 * @…Amachat(未完成)から機能そのままコピペ
	 *
	 *
	 * メイン機能
	 *
	 * ・ローマ字変換@
	 * ・翻訳@
	 * ・チャンネルチャット
	 * ・個人チャット
	 * ・メール(簡易)
	 *
	 * ----------------
	 * ・ミュート
	 * ・非表示
	 * ・ロギング
	 * ・プレイヤー名クリックで個人チャットコマンドを挿入
	 *
	 * -------
	 *
	 * onChat(Bungee) → sendMessage(Bungee) + sendPluginMessage(Bungee):Dynmap用
	 *
	 * ログイン・ログアウトメッセージ表示→LoginInterphoneでやろう
	 *
	 * 死亡ログ・進捗メッセージ共有→だるい
	 *
	 */

	private static Amachat plugin;

	@Override
	public void onEnable(){
		plugin = this;
	}

	@Override
	public void onDisable(){

	}

	public static Amachat getPlugin(){
		return plugin;
	}

}
