package com.stephen.plugin;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class Game {
	
	private Arena arena;
	private HashMap<UUID, Integer> points; 
	
	
	
	public Game(Arena arena) {
		this.arena = arena;
		this.points = new HashMap<>();
	}
	
	public void start() {
		arena.setState(GameState.LIVE);
		arena.sendMessage(ChatColor.GREEN + "Game has started! Your objective is to be the first to break 20 blocks");
		
		arena.updateSign(ChatColor.WHITE + "ARENA " + arena.getID(), ChatColor.GREEN + "LIVE", " ", ChatColor.GRAY + "Player :" + arena.getPlayers().size());
		
		for (UUID uuid : arena.getKits().keySet()) {
			arena.getKits().get(uuid).onStart(Bukkit.getPlayer(uuid));
			
		}
		for (UUID uuid : arena.getPlayers()) {
			points.put(uuid, 0);
			Bukkit.getPlayer(uuid).closeInventory();
		}
	}
	
	public void addPoint(Player player) {
		
		int p = points.get(player.getUniqueId()) + 1;
		
		if (p == 20) {
			arena.sendMessage(ChatColor.GOLD + player.getName() + "WINS!!!");
			
			arena.reset();
			return;
		
		}
		
		points.replace(player.getUniqueId(), p);
		
	}

}
