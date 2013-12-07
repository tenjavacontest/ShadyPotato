package net.amigocraft.entiguard.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.amigocraft.entiguard.EntiGuard;
import net.amigocraft.entiguard.managers.ArmyManager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EntityListener implements Listener {

	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		if (e.getPlayer().hasPermission("entiguard.recruit")){
			ConfigurationSection loyalty =
					EntiGuard.plugin.getConfig().getConfigurationSection("loyalty-chance");
			if (loyalty.contains(e.getPlayer().getItemInHand().toString())){
				if (e.getRightClicked() instanceof LivingEntity){
					LivingEntity mob = (LivingEntity)e.getRightClicked();
					if (EntiGuard.plugin.getConfig().getStringList("allowed-entities")
							.contains(mob.getType().toString())){
						if (new Random().nextInt(loyalty.getInt(
								e.getPlayer().getItemInHand().toString())) == 0){
							mob.setMetadata("leader",
									new FixedMetadataValue(EntiGuard.plugin,
											e.getPlayer().getName()));
							List<UUID> army =
									ArmyManager.armies.containsKey(
											e.getPlayer().getName()) ? ArmyManager.armies.get(
													e.getPlayer().getName()) :
														new ArrayList<UUID>();
													army.add(mob.getUniqueId());
						}
					}
				}
			}
		}
	}

	public void onPlayerQuit(PlayerQuitEvent e){
		ArmyManager.saveEntities(e.getPlayer().getName());
	}
	
	public void onEntityTarget(EntityTargetEvent e){
		if (e.getEntity().hasMetadata("leader")){
			e.setCancelled(true);
		}
	}

}
