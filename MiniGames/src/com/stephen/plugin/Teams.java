package com.stephen.plugin;

import org.bukkit.Material;
import org.bukkit.ChatColor;

public enum Teams {
	
	RED(ChatColor.RED + "RED", Material.RED_WOOL),
	BLUE(ChatColor.BLUE + "BLUE", Material.BLUE_WOOL),
	GREEN(ChatColor.GREEN + "GREEN", Material.GREEN_WOOL);
	
	private String display;
	private Material material;
	
	private Teams(String display, Material material) {
		this.display = display;
		this.material = material;
	}
	
	public String getDisplay() { return display; }
	public Material getMaterial() { return material; }

}
