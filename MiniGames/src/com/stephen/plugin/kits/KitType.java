package com.stephen.plugin.kits;

import org.bukkit.Material;
import org.bukkit.ChatColor;


public enum KitType {
	
	MINER(ChatColor.GOLD + "Miner", Material.DIAMOND_PICKAXE, new String[] {ChatColor.GRAY + "The Best Mining Kit"}),
	FIGHTER(ChatColor.RED + "Fighter", Material.DIAMOND_SWORD, new String[] {ChatColor.GRAY + "The Best Fighting Kit"});
	
	private String display;
	private Material material;
	private String[] description;
	
	private KitType(String display, Material material, String[] description) {
		this.display = display;
		this.material = material;
		this.description = description;
	}
	
	public String getDisplay() {return display; }
	public Material getMaterial() {return material;}
	public String[] getDescriptiom() {return description; }
	

}
