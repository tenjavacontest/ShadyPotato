package net.amigocraft.entiguard.managers;

import net.amigocraft.entiguard.EntiGuard;
import net.amigocraft.entiguard.util.EntityUtil;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityManager {

	public static void manage(){
		for (World w : EntiGuard.plugin.getServer().getWorlds()){
			for (Entity ent : w.getEntities()){
				if (ent instanceof LivingEntity){
					if (ent.hasMetadata("leader")){
						LivingEntity lent = (LivingEntity)ent;
						EntityUtil.moveTo(lent,
								EntiGuard.plugin.getServer().getPlayer(
										ent.getMetadata("leader").get(0).asString())
										.getLocation(), 1f);
					}
				}
			}
		}
	}

}
