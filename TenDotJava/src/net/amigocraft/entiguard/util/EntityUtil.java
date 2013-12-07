package net.amigocraft.entiguard.util;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.Navigation;
import net.minecraft.server.v1_7_R1.PathEntity;

public class EntityUtil {

	public static void moveTo(LivingEntity ent, Location moveTo, float speed){
		Navigation n = ((EntityInsentient)((CraftLivingEntity)ent).getHandle()).getNavigation();
		PathEntity path = n.a(moveTo.getX(), moveTo.getY(), moveTo.getZ());
		n.a(path, speed);
	}

}
