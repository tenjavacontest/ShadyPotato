package net.amigocraft.entiguard.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.amigocraft.entiguard.EntiGuard;
import net.amigocraft.entiguard.util.EntityUtil;

import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ArmyManager {

	public static HashMap<String, List<UUID>> armies = new HashMap<String, List<UUID>>();

	public static void manage(){
		for (String p : armies.keySet()){
			List<UUID> army = ArmyManager.armies.get(p);
			for (World w : EntiGuard.plugin.getServer().getWorlds()){
				for (Entity ent : w.getEntities()){
					if (ent instanceof LivingEntity){
						if (army.contains(ent.getUniqueId())){
							LivingEntity lent = (LivingEntity)ent;
							EntityUtil.moveTo(lent,
									EntiGuard.plugin.getServer().getPlayer(p).getLocation(), 1f);
						}
					}
				}
			}
		}
	}

	public static void saveEntities(String player){
		if (ArmyManager.armies.containsKey(player)){
			List<UUID> army = ArmyManager.armies.get(player);
			for (World w : EntiGuard.plugin.getServer().getWorlds()){
				for (Entity ent : w.getEntities()){
					if (ent instanceof LivingEntity){
						if (army.contains(ent.getUniqueId())){
							LivingEntity lent = (LivingEntity)ent;
							try {
								File f = new File(EntiGuard.plugin.getDataFolder(), "entities.yml");
								YamlConfiguration y = new YamlConfiguration();
								y.load(f);
								for (int i = 0; i < army.size(); i++){
									y.set(player + "." + i + ".world", lent.getWorld().getName());
									y.set(player + "." + i + ".x", lent.getLocation().getX());
									y.set(player + "." + i + ".y", lent.getLocation().getY());
									y.set(player + "." + i + ".z", lent.getLocation().getZ());
									y.set(player + "." + i + ".health", lent.getHealth());
									y.set(player + "." + i + ".name", lent.getCustomName());
								}
							}
							catch (IOException ex){
								ex.printStackTrace();
							}
							catch (InvalidConfigurationException exc) {
								exc.printStackTrace();
							}
						}
					}
					else
						EntiGuard.log.warning("Error saving entity: not a LivingEntity!");
				}
			}
		}
	}

	public static void loadEntities(String player){

	}

}
