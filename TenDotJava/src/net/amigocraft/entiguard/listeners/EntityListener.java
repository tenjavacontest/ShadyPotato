package net.amigocraft.entiguard.listeners;

import java.util.Random;

import net.amigocraft.entiguard.EntiGuard;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityListener implements Listener {

	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		if (e.getPlayer().hasPermission("entiguard.recruit")){
			ConfigurationSection loyalty =
					EntiGuard.plugin.getConfig().getConfigurationSection("loyalty-chance");
			if (loyalty.contains(e.getPlayer().getItemInHand().toString())){
				if (new Random().nextInt(loyalty.getInt(e.getPlayer().getItemInHand().toString()))
						== 0){
					//TODO: Add entity to player's army
				}
			}
		}
	}

}
