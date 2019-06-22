package amata1219.amachat.bungee;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class User {

	private final Amachat amachat = Amachat.getPlugin();

	public final UUID uuid;
	public final Set<User> hidden;

	public User(UUID uuid){
		this.uuid = uuid;
		this.hidden = new HashSet<>();
	}

	public Optional<ProxiedPlayer> getPlayer(){
		return Optional.of(amachat.getProxy().getPlayer(uuid));
	}

	public void sendMessage(User sender, String message){
		if(!hidden.contains(sender))
			sendMessage(message);
	}

	public void sendMessage(String message){
		getPlayer().ifPresent((player) -> player.sendMessage(new TextComponent(message)));
	}

	public void information(String message){
		sendMessage(ChatColor.AQUA + message);
	}

	public void warning(String message){
		sendMessage(ChatColor.RED + message);
	}

	public void addition(String message){
		sendMessage(ChatColor.GRAY + message);
	}

}
