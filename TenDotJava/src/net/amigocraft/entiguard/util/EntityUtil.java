package net.amigocraft.entiguard.util;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.Navigation;
import net.minecraft.server.v1_6_R3.PathEntity;

public class EntityUtil {

	public void moveTo(LivingEntity ent, Location moveTo, float speed){
		Navigation n = ((EntityInsentient)((CraftLivingEntity)ent).getHandle()).getNavigation();
		PathEntity path = n.a(moveTo.getX(), moveTo.getY(), moveTo.getZ());
		n.a(path, speed);
	}

}
