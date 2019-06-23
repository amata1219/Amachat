package amata1219.amachat.bungee;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import amata1219.amachat.bungee.chat.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class User {

	private final Amachat amachat = Amachat.getPlugin();
	private final UserManager userManager = Amachat.getUserManager();

	public final UUID uuid;
	public final String name;
	public boolean useJapanize;
	public final Set<User> hidden;
	public Chat destination;

	public User(ProxiedPlayer player){
		uuid = player.getUniqueId();
		name = player.getName();
		hidden = new HashSet<>();
	}

	public User(UUID uuid, String name, boolean useJapanize, Collection<UUID> hidden){
		this.uuid = uuid;
		this.name = name;
		this.useJapanize = useJapanize;
		this.hidden = new HashSet<>(userManager.wrap(hidden));
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

	public void addition(String message){
		sendMessage(ChatColor.GRAY + message);
	}

	public void warning(String message){
		sendMessage(ChatColor.RED + message);
	}

	public void save(){
		/*
		 * UUID:
		 *   Name:
		 *   UseJapanize:
		 *   Hidden: UUID1,UUID2,UUID3…
		 */
	}

	@Override
	public int hashCode(){
		return uuid.hashCode();
	}

}
