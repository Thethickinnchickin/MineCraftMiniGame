package com.stephen.plugin;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamsGUI {
	
	public TeamsGUI(Player player) {
		
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Team Selection");
		
		for (Teams team : Teams.values()) {
			ItemStack is = new ItemStack(team.getMaterial());
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(team.getDisplay());
			isMeta.setLocalizedName(team.getDisplay());
			isMeta.setLocalizedName(team.name());
			is.setItemMeta(isMeta);
			
			gui.addItem(is);
			
		}
		
		player.openInventory(gui); 
		
	}

}
