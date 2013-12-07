package net.amigocraft.entiguard.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import net.amigocraft.entiguard.EntiGuard;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.Navigation;
import net.minecraft.server.v1_7_R1.PathEntity;

public class EntityUtil {

	public static void moveTo(LivingEntity ent, Location moveTo, float speed){
		Navigation n = ((EntityInsentient)((CraftLivingEntity)ent).getHandle()).getNavigation();
		PathEntity path = n.a(moveTo.getX(), moveTo.getY(), moveTo.getZ());
		n.a(path, speed);
	}

	public static void saveEntities(String player, boolean remove){
		try {
			File f = new File(EntiGuard.plugin.getDataFolder(), "entities.yml");
			YamlConfiguration y = new YamlConfiguration();
			y.load(f);
			ConfigurationSection pSec = y.getConfigurationSection(player);
			if (pSec != null)
				for (String k : pSec.getKeys(true))
					pSec.set(k, null);
			int i = 0;
			List<LivingEntity> r = new ArrayList<LivingEntity>();
			for (World w : EntiGuard.plugin.getServer().getWorlds()){
				for (Entity ent : w.getEntities()){
					if (ent.hasMetadata("leader") &&
							ent.getMetadata("leader").get(0).asString().equals(player)){
						if (ent instanceof LivingEntity){
							LivingEntity lent = (LivingEntity)ent;
							EntiGuard.log.info(lent.getWorld().getName());
							pSec.set(i + ".world", lent.getWorld().getName());
							pSec.set(i + ".x", lent.getLocation().getX());
							pSec.set(i + ".y", lent.getLocation().getY());
							pSec.set(i + ".z", lent.getLocation().getZ());
							pSec.set(i + ".health", lent.getHealth());
							pSec.set(i + ".name", lent.getCustomName());
							pSec.set(i + ".type", lent.getType().toString());
							i += 1;
							if (remove)
								r.add(lent);
						}
						else
							EntiGuard.log.warning("Error saving entity: not a LivingEntity!");
					}
				}
			}
			y.save(f);
			if (remove)
				for (LivingEntity ent : r)
					ent.remove();

		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		catch (InvalidConfigurationException exc) {
			exc.printStackTrace();
		}
	}

	public static void loadEntities(String player){
		try {
			File f = new File(EntiGuard.plugin.getDataFolder(), "entities.yml");
			YamlConfiguration y = new YamlConfiguration();
			y.load(f);
			ConfigurationSection pSec = y.getConfigurationSection(player);
			if (pSec != null){
				for (String k : pSec.getKeys(false)){
					String world = pSec.getString(k + ".world");
					World w = EntiGuard.plugin.getServer().getWorld(world);
					if (w != null){
						LivingEntity lent = (LivingEntity)w.spawnEntity(new Location(w,
								pSec.getInt(k + ".x"),
								pSec.getInt(k + ".y"), pSec.getInt(k + ".z")),
								EntityType.valueOf((pSec.getString(k + ".type"))));
						lent.setMetadata("leader",
								new FixedMetadataValue(EntiGuard.plugin, player));
					}
					else
						EntiGuard.log.warning("Failed to load entity: World is not loaded!");
				}
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		catch (InvalidConfigurationException exc){
			exc.printStackTrace();
		}
	}

}
