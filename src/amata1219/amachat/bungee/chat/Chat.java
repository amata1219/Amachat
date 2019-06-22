package amata1219.amachat.bungee.chat;

import java.util.Set;
import java.util.UUID;

import amata1219.amachat.bungee.Amachat;
import amata1219.amachat.bungee.User;
import amata1219.amachat.bungee.UserManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface Chat {

	String getName();

	String getAlias();

	void setAlias(String alias);

	Set<User> getMembers();

	default boolean isMember(ProxiedPlayer player){
		return isMember(player.getUniqueId());
	}

	default boolean isMember(UUID uuid){
		return isMember(getUserManager().wrap(uuid));
	}

	default boolean isMember(User user){
		return getMembers().contains(user);
	}

	Set<User> getMuted();

	default boolean isMuted(ProxiedPlayer player){
		return isMuted(player.getUniqueId());
	}

	default boolean isMuted(UUID uuid){
		return isMuted(getUserManager().wrap(uuid));
	}

	default boolean isMuted(User user){
		return getMuted().contains(user);
	}

	Set<User> getBanned();

	default boolean isBanned(ProxiedPlayer player){
		return isBanned(player.getUniqueId());
	}

	default boolean isBanned(UUID uuid){
		return isBanned(getUserManager().wrap(uuid));
	}

	default boolean isBanned(User user){
		return getBanned().contains(user);
	}

	default boolean canJoin(ProxiedPlayer player){
		return canJoin(player.getUniqueId());
	}

	default boolean canJoin(UUID uuid){
		return canJoin(getUserManager().wrap(uuid));
	}

	default boolean canJoin(User user){
		return !isBanned(user) && !isMember(user);
	}

	default void join(ProxiedPlayer player){
		join(player.getUniqueId());
	}

	default void join(UUID uuid){
		join(uuid);
	}

	void join(User user);

	default void leave(ProxiedPlayer player){
		leave(player.getUniqueId());
	}

	default void leave(UUID uuid){
		leave(uuid);
	}


	void leave(User user);

	default void kick(ProxiedPlayer player){
		kick(player.getUniqueId());
	}

	default void kick(UUID uuid){
		kick(uuid);
	}

	void kick(User user);

	default void mute(ProxiedPlayer player){
		mute(player.getUniqueId());
	}

	default void mute(UUID uuid){
		mute(uuid);
	}

	void mute(User user);

	default void unmute(ProxiedPlayer player){
		unmute(player.getUniqueId());
	}

	default void unmute(UUID uuid){
		unmute(uuid);
	}

	void unmute(User user);

	default void ban(ProxiedPlayer player){
		ban(player.getUniqueId());
	}

	default void ban(UUID uuid){
		ban(uuid);
	}

	void ban(User user);

	default void unban(ProxiedPlayer player){
		unban(player.getUniqueId());
	}

	default void unban(UUID uuid){
		unban(uuid);
	}

	void unban(User user);

	default void chat(ProxiedPlayer player, String message){
		chat(player, message);
	}

	default void chat(User sender, String message){
		if(isMuted(sender)){
			sender.warning("あなたは" + getAlias() + "ではミュートされています。");
			return;
		}

		getUserManager().getOnlineUsers()
			.stream()
			.filter(this::isMember)
			.forEach(member -> member.sendMessage(sender, message));
	}

	default void broadcast(String message){
		getMembers().forEach(member -> member.sendMessage(message));
	}

	default UserManager getUserManager(){
		return Amachat.getUserManager();
	}

}
