package amata1219.chat.plugin.bungee;

import org.bukkit.event.Listener;

import amata1219.chat.plugin.bungee.fucntion.HolderPlacer;
import amata1219.chat.plugin.bungee.fucntion.RomanConverter;
import amata1219.chat.plugin.bungee.fucntion.Translator;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	//メインクラスにもたせておくかも
	private final RomanConverter converter;
	private final Translator translator;
	private final HolderPlacer placer;

	public ChatListener(RomanConverter converter, Translator translator, HolderPlacer placer){
		this.converter = converter;
		this.translator = translator;
		this.placer = placer;
	}

	@EventHandler
	public void onChat(ChatEvent event){
		/*
		 * コマンドであれば弾く
		 *
		 * チャンネルチャットに参加しているかどうか
		 * テキスト処理は別で
		 *
		 *
		 */

		if(event.isCommand())
			return;

		//チャット共有のためデフォでキャンセル
		//位置がまだ定まらないためコメントアウト
		//event.setCancelled(true);

		Connection senderConnection = event.getSender();
		if(!isProxiedPlayer(senderConnection))
			return;

		ProxiedPlayer sender = (ProxiedPlayer) senderConnection;

		/*
		 * テキスト処理
		 *
		 * ・チャンネルチャットのprefix対応
		 * ・ローマ字変換 対応済み
		 * ・翻訳 対応済み
		 * ・プレースホルダ(オリジナル)
		 * ・クリックイベント埋め込み(この時点でComponent)
		 *
		 * Amachat見てくる_(:3 ∠ )_
		 *
		 */
	}

	//後から別の場所へ移動
	public boolean isProxiedPlayer(Connection connection){
		return connection instanceof ProxiedPlayer;
	}

}
