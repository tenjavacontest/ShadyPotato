package net.amigocraft.entiguard;

import java.util.logging.Logger;

import net.amigocraft.entiguard.listeners.EntityListener;

import org.bukkit.plugin.java.JavaPlugin;

public class EntiGuard extends JavaPlugin {

	public static EntiGuard plugin;
	public static Logger log;
	
	public void onEnable(){
		log = getLogger();
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		plugin = this;
	}
	
	public void onDisable(){
		plugin = null;
	}
	
}
