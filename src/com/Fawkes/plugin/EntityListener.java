package com.Fawkes.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
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

		this.warn = ChatColor.RED + "You have exceeded the number of entities in this chunk!";

	}

    @EventHandler
    public void entitySpawnEvent(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Blaze || e.getEntity() instanceof Ghast) {
            this.warnPlayers(e.getLocation(), "You are not allowed to spawn Ghasts or Blazes!");
            e.setCancelled(true);
            return;
        }
        
        int mobs = 0;
        
        for (Entity ent : e.getLocation().getChunk().getEntities()) {
            if (!(ent instanceof LivingEntity)) continue;
            
            if (ent instanceof Player) continue;
            
            ++mobs;
        }
        
        if (mobs > this.maxMobs) {
            this.warnPlayers(e.getLocation(), "You have exceeded the number of mobs in this chunk!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void itemSpawnEvent(ItemSpawnEvent e) {
        if (e.getLocation().getChunk().getEntities().length > this.max) {
            this.warnPlayers(e.getLocation(), this.warn);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void entityLaunchEvent(ProjectileLaunchEvent e) {
        if (e.getEntity().getLocation().getChunk().getEntities().length > this.max) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void hangingPlaceEvent(HangingPlaceEvent e) {
        if ((e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting) && e.getEntity().getLocation().getChunk().getEntities().length > this.max) {
            e.getPlayer().sendMessage(this.warn);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void vehiclePlaceEvent(VehicleCreateEvent e) {
        if (e.getVehicle().getLocation().getChunk().getEntities().length > this.max) {
            e.getVehicle().remove();
        }
    }

    public void warnPlayers(Location loc, String msg) {
        for (Entity e : loc.getChunk().getEntities()) {
            if (!(e instanceof Player)) continue;
            ((Player)e).sendMessage(msg);
        }
    }

}
