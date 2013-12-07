package net.amigocraft.entiguard.managers;

import java.io.File;
import java.io.IOException;

import net.amigocraft.entiguard.EntiGuard;
import net.amigocraft.entiguard.util.EntityUtil;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ArmyManager {

	public static void manage(){
		for (World w : EntiGuard.plugin.getServer().getWorlds()){
			for (Entity ent : w.getEntities()){
				if (ent instanceof LivingEntity){
					if (ent.hasMetadata("leader")){
						LivingEntity lent = (LivingEntity)ent;
						EntiGuard.log.info(ent.getMetadata("leader").get(0).asString());
						EntityUtil.moveTo(lent,
								EntiGuard.plugin.getServer().getPlayer(
										ent.getMetadata("leader").get(0).asString())
										.getLocation(), 1f);
					}
				}
			}
		}
	}

	public static void saveEntities(String player){
		try {
			File f = new File(EntiGuard.plugin.getDataFolder(), "entities.yml");
			YamlConfiguration y = new YamlConfiguration();
			y.load(f);
			ConfigurationSection pSection = y.getConfigurationSection(player);
			if (pSection != null)
				for (String k : pSection.getKeys(true))
					pSection.set(k, null);
			int i = 0;
			for (World w : EntiGuard.plugin.getServer().getWorlds()){
				for (Entity ent : w.getEntities()){
					if (ent instanceof LivingEntity){
						if (ent.getMetadata("leader").equals(player)){
							LivingEntity lent = (LivingEntity)ent;
							y.set(player + "." + i + ".world", lent.getWorld().getName());
							y.set(player + "." + i + ".x", lent.getLocation().getX());
							y.set(player + "." + i + ".y", lent.getLocation().getY());
							y.set(player + "." + i + ".z", lent.getLocation().getZ());
							y.set(player + "." + i + ".health", lent.getHealth());
							y.set(player + "." + i + ".name", lent.getCustomName());
							i += 1;
						}
					}
					else
						EntiGuard.log.warning("Error saving entity: not a LivingEntity!");
				}
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		catch (InvalidConfigurationException exc) {
			exc.printStackTrace();
		}
	}

	public static void loadEntities(String player){

	}

}
