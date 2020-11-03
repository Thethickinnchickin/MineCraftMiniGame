package com.stephen.plugin;

import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.stephen.plugin.kits.KitType;

import org.bukkit.event.inventory.InventoryClickEvent;

public class GameListener implements Listener{
	
	@EventHandler 
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem() != null && e.getView().getTitle().contains("Team Selection") && e.getRawSlot() < 54) {
			Teams team = Teams.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
			
			if (Manager.getArena(player).getTeam(player).equals(team)) {
				player.sendMessage(ChatColor.GRAY + "You are now on " + team.getDisplay() + ChatColor.GRAY + " team!");
				Manager.getArena(player).setTeam(player, team);
			} else {
				player.sendMessage(ChatColor.GRAY + "You are already on " + team.getDisplay() + ChatColor.GRAY + " team!");
			}
			
			e.setCancelled(true);
			player.closeInventory();
		}
	}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getView().getTitle().contains("Kit Selection") && e.getRawSlot() <= 54 && e.getCurrentItem() != null) {
			KitType type = KitType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
			
			if (Manager.hasKit(player) && Manager.getKit(player).equals(type)) {
				player.sendMessage(ChatColor.RED + "YOu have alreasy chosen this kit");
			} else {
				player.sendMessage(ChatColor.GREEN + "You have equipped the " + type.getDisplay() + ChatColor.GREEN + " Kit!");
				Manager.getArena(player).setKit(player.getUniqueId(), type);
			}
			
			e.setCancelled(true);
			player.closeInventory();
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		
		if (Manager.isPlaying(player) && Manager.getArena(player).getState().equals(GameState.LIVE)) {
			
			player.sendMessage(ChatColor.GOLD + "+1 point!");
			
			Manager.getArena(player).getGame().addPoint(player);
			
			
	
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		if(Manager.isPlaying(player)) {
			Manager.getArena(player).removePlayer(player);
		}
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		
		if (Manager.isArenaWorld(e.getWorld())) {
			Manager.getArena(e.getWorld()).setJoinState(true);
		}
		
		
		
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (e.hasBlock() && e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
			int id = Config.isSign(e.getClickedBlock().getLocation());
			
			if (id != -1) {
				
				if (Manager.getArena(id).getState().equals(GameState.COUNTDOWN)
						|| Manager.getArena(id).getState().equals(GameState.RECRUITING)) {
					if (Manager.getArena(id).getPlayers().contains(e.getPlayer().getUniqueId())) {
						Manager.getArena(id).addPlayer(player);
					} else {
						player.sendMessage(ChatColor.RED + "You are already in this game!");
					}
				}
				
				
				e.setCancelled(true);
			}
		}
	}

}
