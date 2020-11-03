package com.stephen.plugin.kits.types;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.stephen.plugin.kits.Kit;
import com.stephen.plugin.kits.KitType;

public class Fighter extends Kit{
	
	public Fighter(UUID uuid) {
		super(uuid, KitType.FIGHTER);
	}
	
	@Override
	public void onStart(Player player) {
		player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
		player.getInventory().addItem(new ItemStack(Material.TORCH));
		
	}


}
