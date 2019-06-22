package amata1219.amachat.bungee.chat;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;

import amata1219.amachat.bungee.User;
import amata1219.amachat.collection.LockableHashSet;
import amata1219.amachat.collection.LockableHashSet.LockableHashSetLocker;

public class ChannelChat implements Chat {

	private final String name;
	private String alias;
	private final LockableHashSetLocker<User> members, muted, banned;

	public ChannelChat(String name, String alias, Collection<UUID> members, Collection<UUID> muted, Collection<UUID> banned){
		this.name = name;
		this.alias = alias;
		this.members = LockableHashSet.of(getUserManager().wrap(members));
		this.muted =  LockableHashSet.of(getUserManager().wrap(muted));
		this.banned =  LockableHashSet.of(getUserManager().wrap(banned));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public void setAlias(String alias) {
		Validate.notNull(alias, "Alias can not be null");
		this.alias = alias;
	}

	@Override
	public Set<User> getMembers() {
		return members.set;
	}

	@Override
	public Set<User> getMuted() {
		return muted.set;
	}

	@Override
	public Set<User> getBanned() {
		return banned.set;
	}

	@Override
	public void join(User user) {
	}

	@Override
	public void leave(User user) {
	}

	@Override
	public void kick(User user) {
	}

	@Override
	public void mute(User user) {
	}

	@Override
	public void unmute(User user) {
	}

	@Override
	public void ban(User user) {
	}

	@Override
	public void unban(User user) {
	}

	@Override
	public void chat(User user, String message) {
	}

}
