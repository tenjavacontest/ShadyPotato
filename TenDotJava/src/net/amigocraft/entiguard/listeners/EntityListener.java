package net.amigocraft.entiguard.listeners;

import java.util.Random;

import net.amigocraft.entiguard.EntiGuard;
import net.amigocraft.entiguard.util.EntityUtil;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EntityListener implements Listener {

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		if (e.getPlayer().hasPermission("entiguard.recruit")){
			ConfigurationSection loyalty =
					EntiGuard.plugin.getConfig().getConfigurationSection("loyalty-chance");
			EntiGuard.log.info(e.getPlayer().getItemInHand().getType().toString());
			if (loyalty.contains(e.getPlayer().getItemInHand().getType().toString())){
				if (e.getRightClicked() instanceof LivingEntity){
					LivingEntity mob = (LivingEntity)e.getRightClicked();
					if (EntiGuard.plugin.getConfig().getStringList("allowed-entities")
							.contains(mob.getType().toString())){
						if (new Random().nextInt(loyalty.getInt(
								e.getPlayer().getItemInHand().getType().toString())) == 0){
							mob.setMetadata("leader",
									new FixedMetadataValue(EntiGuard.plugin,
											e.getPlayer().getName()));
							EntityUtil.saveEntities(e.getPlayer().getName(), false);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		EntityUtil.loadEntities(e.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		EntityUtil.saveEntities(e.getPlayer().getName(), true);
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent e){
		if (e.getEntity().hasMetadata("leader"))
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof LivingEntity && !(e.getDamager() instanceof Player)){
			if (e.getEntity() instanceof Player){
				if (e.getDamager().hasMetadata("leader") &&
						e.getDamager().getMetadata("leader").get(0).asString()
						.equals(((Player)e.getEntity()).getName()))
					e.setCancelled(true);
			}
		}
		else if (e.getDamager() instanceof Player){
			for (Entity ent : e.getDamager().getNearbyEntities(10, 10, 10))
				if (ent instanceof LivingEntity)
					if (ent.hasMetadata("leader") &&
							ent.getMetadata("leader").get(0).asString()
							.equals(((Player)e.getDamager()).getName())){

					}
		}
	}

}
