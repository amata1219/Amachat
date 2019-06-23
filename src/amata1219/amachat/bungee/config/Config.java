package amata1219.amachat.bungee.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.ByteStreams;

import amata1219.amachat.bungee.Amachat;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {

	private static final ConfigurationProvider PROVIDER = ConfigurationProvider.getProvider(YamlConfiguration.class);

	private final Amachat plugin = Amachat.getPlugin();

	public final String name;

	private final File file;
	protected Configuration config;

	public Config(String name){
		this.name = name;
		this.file = new File(plugin.getDataFolder(), name);
	}

	public Config(File folder, String name){
		this.name = name;
		this.file = new File(folder, name);
	}

	public Config create(){
		reload();
		return this;
	}

	public Configuration get(){
		return config == null ? reload().config : config;
	}

	public void save(){
		try {
			PROVIDER.save(config, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Config reload(){
		if(!file.exists()){
			try{
				plugin.getDataFolder().mkdirs();
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}

			try(FileOutputStream out = new FileOutputStream(file);
					InputStream in = plugin.getResourceAsStream(name)){
				ByteStreams.copy(in, out);
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		try {
			config = PROVIDER.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}

	public void update(){
		save();
		reload();
	}

}
