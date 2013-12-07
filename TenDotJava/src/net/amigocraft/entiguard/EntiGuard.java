package net.amigocraft.entiguard;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.amigocraft.entiguard.listeners.EntityListener;
import net.amigocraft.entiguard.managers.EntityManager;
import net.amigocraft.entiguard.util.EntityUtil;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EntiGuard extends JavaPlugin {

	public static EntiGuard plugin;
	public static Logger log;

	public void onEnable(){
		log = getLogger();
		saveDefaultConfig();
		try {new File(getDataFolder(), "entities.yml").createNewFile();}
		catch (IOException ex){ex.printStackTrace();}
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		plugin = this;
		for (Player p : getServer().getOnlinePlayers())
			EntityUtil.loadEntities(p.getName());
		getServer().getScheduler().runTaskTimer(this, new Runnable(){
			public void run(){
				EntityManager.manage();
			}
		}, 0L, 10L);
	}

	public void onDisable(){
		for (Player p : getServer().getOnlinePlayers())
			EntityUtil.saveEntities(p.getName(), true);
		plugin = null;
	}

}
