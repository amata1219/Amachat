package amata1219.amachat.bungee.chat;

import java.util.Set;
import java.util.stream.Collectors;

import amata1219.amachat.bungee.NotImplementedException;
import amata1219.amachat.bungee.User;
import amata1219.amachat.collection.LockableHashSet;
import amata1219.amachat.collection.LockableHashSet.LockableHashSetLocker;
import net.md_5.bungee.config.Configuration;

public class GlobalChat implements Chat {

	private final String alias;
	private final LockableHashSetLocker<User> muted, banned;

	public GlobalChat(String alias){
		this.alias = alias;
		this.muted = LockableHashSet.of();
		this.banned = LockableHashSet.of();
	}

	@Override
	public String getName() {
		return "GlobalChat";
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public void setAlias(String alias) {
		new NotImplementedException();
	}

	@Override
	public Set<User> getMembers() {
		return getUserManager().getUsers()
					.stream()
					.filter(user -> !isBanned(user))
					.collect(Collectors.toSet());
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
		new NotImplementedException();
	}

	@Override
	public void leave(User user) {
		new NotImplementedException();
	}

	@Override
	public void kick(User user) {
		new NotImplementedException();
	}

	@Override
	public void mute(User user) {
		muted.bypass((set) -> set.add(user));

		user.warning(getName() + "からミュートされました。");
	}

	@Override
	public void unmute(User user) {
		muted.bypass((set) -> set.remove(user));

		user.information(getName() + "からミュートが解除されました。");
	}

	@Override
	public void ban(User user) {
		banned.bypass((set) -> set.add(user));

		user.warning(getName() + "からBANされました。");
	}

	@Override
	public void unban(User user) {
		banned.bypass((set) -> set.remove(user));

		user.information(getName() + "からBANが解除されました。");
	}

	@Override
	public void save(Configuration config) {
		config.set(getName() + "Alias", alias);
		config.set(getName() + "Muted", getMuted().toString());
		config.set(getName() + "Banned", getBanned().toString());
	}

}
