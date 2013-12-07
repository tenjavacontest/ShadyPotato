package net.amigocraft.entiguard;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.amigocraft.entiguard.listeners.EntityListener;
import net.amigocraft.entiguard.managers.ArmyManager;

import org.bukkit.plugin.java.JavaPlugin;

public class EntiGuard extends JavaPlugin {

	public static EntiGuard plugin;
	public static Logger log;
	
	public void onEnable(){
		log = getLogger();
		saveDefaultConfig();
		try {new File(getDataFolder(), "entites.yml").createNewFile();}
		catch (IOException ex){ex.printStackTrace();}
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		plugin = this;
		getServer().getScheduler().runTaskTimer(this, new Runnable(){
			public void run(){
				ArmyManager.manage();
			}
		}, 0L, 10L);
	}
	
	public void onDisable(){
		plugin = null;
	}
	
}
