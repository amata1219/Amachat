package amata1219.amachat.bungee.chat;

import java.util.Set;
import java.util.UUID;

import amata1219.amachat.bungee.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface Chat {

	String getName();

	String getAlias();

	void setAlias(String alias);

	Set<UUID> getMembers();

	default boolean isMember(User user){
		return isMember(user.uuid);
	}

	default boolean isMember(ProxiedPlayer player){
		return isMember(player.getUniqueId());
	}

	default boolean isMember(UUID uuid){
		return getMembers().contains(uuid);
	}

	Set<UUID> getMuted();

	default boolean isMuted(User user){
		return isMuted(user.uuid);
	}

	default boolean isMuted(ProxiedPlayer player){
		return isMuted(player.getUniqueId());
	}

	default boolean isMuted(UUID uuid){
		return getMuted().contains(uuid);
	}

	Set<UUID> getBanned();

	default boolean isBanned(User user){
		return isBanned(user.uuid);
	}

	default boolean isBanned(ProxiedPlayer player){
		return isBanned(player.getUniqueId());
	}

	default boolean isBanned(UUID uuid){
		return getBanned().contains(uuid);
	}

	default boolean canJoin(UUID uuid){
		return !isBanned(uuid) && !isMember(uuid);
	}

	default void join(User user){
		join(user.uuid);
	}

	default void join(ProxiedPlayer player){
		join(player.getUniqueId());
	}

	void join(UUID uuid);

	default void leave(User user){
		leave(user.uuid);
	}

	default void leave(ProxiedPlayer player){
		leave(player.getUniqueId());
	}

	void leave(UUID uuid);

	default void kick(User user){
		kick(user.uuid);
	}

	default void kick(ProxiedPlayer player){
		kick(player.getUniqueId());
	}

	void kick(UUID uuid);

	default void mute(User user){
		mute(user.uuid);
	}

	default void mute(ProxiedPlayer player){
		mute(player.getUniqueId());
	}

	void mute(UUID uuid);

	default void unmute(User user){
		unmute(user.uuid);
	}

	default void unmute(ProxiedPlayer player){
		unmute(player.getUniqueId());
	}

	void unmute(UUID uuid);

	default void ban(User user){
		ban(user.uuid);
	}

	default void ban(ProxiedPlayer player){
		ban(player.getUniqueId());
	}

	void ban(UUID uuid);

	default void unban(User user){
		unban(user.uuid);
	}

	default void unban(ProxiedPlayer player){
		unban(player.getUniqueId());
	}

	void unban(UUID uuid);

}
