package amata1219.amachat.bungee;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;

import amata1219.amachat.collection.LockableHashSet;
import amata1219.amachat.collection.LockableHashSet.LockableHashSetLocker;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class UserManager implements Listener {

	private final HashMap<UUID, User> users;
	private final LockableHashSetLocker<User> onlineUsers = LockableHashSet.of();

	public UserManager(){
		users = new HashMap<>();
	}

	public Set<User> getUsers(){
		return new HashSet<>(users.values());
	}

	public Set<User> getOnlineUsers(){
		return onlineUsers.set;
	}

	public Set<User> wrap(Collection<UUID> uuids){
		return uuids.stream()
				.map(users::get)
				.collect(Collectors.toSet());
	}

	public User wrap(ProxiedPlayer player){
		Validate.notNull(player, "Player can not be null");
		return wrap(player.getUniqueId());
	}

	public User wrap(UUID uuid){
		return users.get(uuid);
	}

	@EventHandler
	public void onJoin(ServerConnectEvent event){
		UUID uuid = event.getPlayer().getUniqueId();
		User user = users.getOrDefault(uuid, users.put(uuid, new User(uuid)));
		onlineUsers.bypass((set) -> set.add(user));
	}

	@EventHandler
	public void onQuit(ServerDisconnectEvent event){
		onlineUsers.bypass((set) -> set.remove(users.get(event.getPlayer().getUniqueId())));
	}

}
