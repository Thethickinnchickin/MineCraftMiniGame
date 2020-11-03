package com.stephen.plugin.kits;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.HandlerList;
import org.bukkit.Bukkit;

import com.stephen.plugin.Main;



public abstract class Kit implements Listener {
	
	protected static UUID uuid;
	protected KitType type;
	
	public Kit(UUID uuid, KitType type) {
		this.uuid = uuid;
		this.type = type;
		
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	public static UUID getUUID() { return uuid; }
	public KitType getType() {return type; }
	
	public abstract void onStart(Player player);
	
	public void remove() {
		HandlerList.unregisterAll(this);
	}

}
