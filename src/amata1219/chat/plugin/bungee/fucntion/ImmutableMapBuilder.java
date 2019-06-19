package amata1219.chat.plugin.bungee.fucntion;

import com.google.common.collect.ImmutableMap;

public class ImmutableMapBuilder {

	public final ImmutableMap.Builder<String, String[]> builder = new ImmutableMap.Builder<String, String[]>();

	public ImmutableMapBuilder(){

	}

	public ImmutableMapBuilder put(String key, String... args){
		if(args.length != 5)
			throw new IllegalArgumentException("Invalid args");
		else
			builder.put(key, args);

		return this;
	}

}
