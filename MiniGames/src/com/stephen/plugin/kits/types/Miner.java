package com.stephen.plugin.kits.types;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;

import com.stephen.plugin.kits.Kit;
import com.stephen.plugin.kits.KitType;


public class Miner extends Kit{
	
	public Miner(UUID uuid) {
		super(uuid, KitType.MINER);
	}

	@Override
	public void onStart(Player player) {
		player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
		player.getInventory().addItem(new ItemStack(Material.TORCH));
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		UUID player = e.getPlayer().getUniqueId();
		UUID uuid = Kit.getUUID();
		
		if(player == uuid) {
			System.out.println(e.getPlayer().getName() + " Just broke a block");
		}
		
		
	}

}
