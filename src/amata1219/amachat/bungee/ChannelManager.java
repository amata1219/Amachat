package amata1219.amachat.bungee;

import java.util.List;

import amata1219.amachat.bungee.chat.ChannelChat;
import amata1219.amachat.collection.LockableArrayList.LockableArrayListLocker;

public class ChannelManager {

	private final LockableArrayListLocker<ChannelChat> channels;

	public ChannelManager(){

	}

	public List<ChannelChat> getChannels(){
		return channels.list;
	}

}
