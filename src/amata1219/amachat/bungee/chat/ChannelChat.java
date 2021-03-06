package amata1219.amachat.bungee.chat;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;

import amata1219.amachat.bungee.User;
import amata1219.amachat.collection.LockableHashSet;
import amata1219.amachat.collection.LockableHashSet.LockableHashSetLocker;
import net.md_5.bungee.config.Configuration;

public class ChannelChat implements Chat {

	private final String name;
	private String alias, prefix;
	private final LockableHashSetLocker<User> members, muted, banned;

	public ChannelChat(String name, String alias, String prefix,Collection<UUID> members, Collection<UUID> muted, Collection<UUID> banned){
		this.name = name;
		this.alias = alias;
		this.prefix = prefix;
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

	public String getPrefix(){
		return prefix;
	}

	public boolean hasPrefix(){
		return prefix != null;
	}

	public void setPrefix(String prefix){
		Validate.notNull(prefix, "Prefix can not be null");

		//unregister -> prefix = prefix -> register
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
		if(!canJoin(user))
			return;

		addMember(user);

		user.information(name + "に参加しました。");
		information(user.name + "さんが" + name + "に参加しました。");
	}

	@Override
	public void leave(User user) {
		if(!isMember(user))
			return;

		removeMember(user);

		user.information(name + "から退出しました。");
		information(user.name + "さんが" + name + "から退出しました。");
	}

	@Override
	public void kick(User user) {
		if(!isMember(user))
			return;

		removeMember(user);

		user.warning(name + "からキックされました。");
	}

	@Override
	public void mute(User user) {
		muted.bypass((set) -> set.add(user));

		if(!isMember(user))
			return;

		removeMember(user);

		user.warning(name + "からミュートされました。");
	}

	@Override
	public void unmute(User user) {
		muted.bypass((set) -> set.remove(user));

		user.information(name + "からミュートが解除されました。");
	}

	@Override
	public void ban(User user) {
		banned.bypass((set) -> set.add(user));

		if(!isMember(user))
			return;

		removeMember(user);

		user.warning(name + "からBANされました。");
	}

	@Override
	public void unban(User user) {
		banned.bypass((set) -> set.remove(user));

		user.information(name + "からBANが解除されました。");
	}

	public void save(Configuration config){
		config.set(name + "Alias", alias);
		config.set(name + "Prefix", prefix);
		config.set(name + "Members", members.toString());
		config.set(name + "Muted", muted.toString());
		config.set(name + "Banned", banned.toString());
	}

	private void addMember(User user){
		members.bypass((set) -> set.add(user));
	}

	private void removeMember(User member){
		members.bypass((set) -> set.remove(member));
	}

}
