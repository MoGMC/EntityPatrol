package com.Fawkes.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class EntityPatrol extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new EntityListener(25, 12), this);

	}

	@Override
	public void onDisable() {

	}

}
