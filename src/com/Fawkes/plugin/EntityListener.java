package com.Fawkes.plugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class EntityListener implements Listener {

	private String warn;

	private int max, maxMobs;

	public EntityListener(int max, int maxMobs) {
		this.max = max;
		this.maxMobs = maxMobs;

		warn = ChatColor.RED + "You have exceeded the number of entities in this chunk!";

	}

	@EventHandler
	public void entitySpawnEvent(CreatureSpawnEvent e) {

		int mobs = 0;

		for (Entity ent : e.getLocation().getChunk().getEntities()) {
			if (ent instanceof LivingEntity) {
				mobs++;

			}
		}

		if (mobs > maxMobs) {

			e.setCancelled(true);

		}

	}

	@EventHandler
	public void itemSpawnEvent(ItemSpawnEvent e) {

		if (e.getLocation().getChunk().getEntities().length > max) {

			e.setCancelled(true);

		}

	}

	@EventHandler
	public void entityLaunchEvent(ProjectileLaunchEvent e) {

		if (e.getEntity().getLocation().getChunk().getEntities().length > max) {

			e.setCancelled(true);

		}

	}

	@EventHandler
	public void hangingPlaceEvent(HangingPlaceEvent e) {

		if (e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting) {
			if (e.getEntity().getLocation().getChunk().getEntities().length > max) {

				e.getPlayer().sendMessage(warn);
				e.setCancelled(true);

			}
		}

	}

	@EventHandler
	public void vehiclePlaceEvent(VehicleCreateEvent e) {
		if (e.getVehicle().getLocation().getChunk().getEntities().length > max) {

			e.getVehicle().remove();

		}

	}

}
