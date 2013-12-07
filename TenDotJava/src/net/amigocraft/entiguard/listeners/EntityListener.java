package net.amigocraft.entiguard.listeners;

import java.util.Random;

import net.amigocraft.entiguard.EntiGuard;
import net.amigocraft.entiguard.managers.ArmyManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EntityListener implements Listener {

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		EntiGuard.log.info("fired");
		if (e.getPlayer().hasPermission("entiguard.recruit")){
			EntiGuard.log.info("permission");
			ConfigurationSection loyalty =
					EntiGuard.plugin.getConfig().getConfigurationSection("loyalty-chance");
			for (String k : loyalty.getKeys(false))
				EntiGuard.log.info(k);
			EntiGuard.log.info(e.getPlayer().getItemInHand().getType().toString());
			if (loyalty.contains(e.getPlayer().getItemInHand().getType().toString())){
				EntiGuard.log.info("allowed1");
				if (e.getRightClicked() instanceof LivingEntity){
					EntiGuard.log.info("alive");
					LivingEntity mob = (LivingEntity)e.getRightClicked();
					if (EntiGuard.plugin.getConfig().getStringList("allowed-entities")
							.contains(mob.getType().toString())){
						EntiGuard.log.info("allowed2");
						if (new Random().nextInt(loyalty.getInt(
								e.getPlayer().getItemInHand().getType().toString())) == 0){
							EntiGuard.log.info("chance");
							mob.setMetadata("leader",
									new FixedMetadataValue(EntiGuard.plugin,
											e.getPlayer().getName()));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		ArmyManager.saveEntities(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent e){
		if (e.getEntity().hasMetadata("leader")){
			e.setCancelled(true);
		}
	}

}
