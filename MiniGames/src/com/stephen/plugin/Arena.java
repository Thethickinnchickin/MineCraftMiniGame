package com.stephen.plugin;

import java.util.ArrayList;

import java.util.UUID;
import java.util.List;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;


import com.stephen.plugin.kits.Kit;
import com.stephen.plugin.kits.KitType;
import com.stephen.plugin.kits.types.Fighter;
import com.stephen.plugin.kits.types.Miner;

import com.google.common.collect.TreeMultimap;

public class Arena {
	
	private int id;
	private ArrayList<UUID> players;
	private HashMap<UUID, Kit> kits;
	private HashMap<UUID, Teams> teams;
	private Location spawn;
	private Location sign;
	private static GameState state;
	private Countdown countdown;
	private Game game;
	
	private boolean canJoin; 
	
	public Arena(int id) {

		this.id = id;
		players = new ArrayList<>();
		teams = new HashMap<>();
		spawn = Config.getArenaSpawn(id);
		state = GameState.RECRUITING;
		countdown = new Countdown(this);
		game = new Game(this);
		kits = new HashMap<>();
		sign = Config.getArenaSignSpawn(id);
		
		canJoin = true;
		
		updateSign(ChatColor.WHITE + "ARENA " + id, ChatColor.GOLD + "RECRUITING", " ",ChatColor.GRAY + "Click to join");
		
		
	}
	
	public void start() {
		game.start();
	}
	
	public void reset() {
		
		
		for (UUID uuid : players) {
			Bukkit.getPlayer(uuid).teleport(Config.getLobbySpawn());
			removeKit(uuid);
			Bukkit.getPlayer(uuid).getInventory().clear();
		}
		
		state = GameState.RECRUITING;
		countdown = new Countdown(this);
		game = new Game(this);
		players.clear();
		teams.clear();
		
		Bukkit.unloadWorld(spawn.getWorld().getName(), false);
		canJoin = false; 
		
		
		
		spawn = Config.getArenaSpawn(id);
		
		updateSign(ChatColor.WHITE + "ARENA " + id, ChatColor.GOLD + "RECRUITING", " ",ChatColor.GRAY + "Click to join");
		
		
	}
	
	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
		player.teleport(spawn);
		
		TreeMultimap<Integer, Teams> count = TreeMultimap.create();
		
		for (Teams team : Teams.values()) {
			count.put(getTeamCount(team), team);
		}
		
		Teams selected = (Teams) count.values().toArray()[0];
		
		setTeam(player, selected);
		
		player.sendMessage(ChatColor.GRAY + "You have been placed on " + selected.getDisplay() + " team!");
		
		if (players.size() >= Config.getRequiredlayers()) {
			countdown.begin();
		}
		
	}
	
	public void updateSign(String line1, String line2, String line3, String line4) {
		Sign wallSign = (Sign) sign.getBlock().getState(); 
		wallSign.setLine(0, line1);
		wallSign.setLine(1, line2);
		wallSign.setLine(2, line3);
		wallSign.setLine(3, line4);
		wallSign.update();
	}
	
	public void sendMessage(String message) {
		for (UUID uuid : players) {
			Bukkit.getPlayer(uuid).sendMessage(message);
		}
	}
	
	public void removePlayer(Player player) {

		players.remove(player.getUniqueId());
		player.teleport(Config.getLobbySpawn());
		
		UUID uuid = player.getUniqueId();
		
		removeKit(uuid);
		
		player.getInventory().clear();
		
		removeTeam(player);
		
		if (players.size() >= Config.getRequiredlayers() && state.equals(GameState.COUNTDOWN)) {
			reset();
		}
		
		if (players.size() == 0 && state.equals(GameState.LIVE)) {
			reset();
		}
		
		if (state.equals(GameState.LIVE)) {
			updateSign(ChatColor.WHITE + "ARENA " + id, ChatColor.BLUE + "LIVE", " ", ChatColor.GRAY + "Players: " + players.size());
		}
	}
	public Game getGame() {return game; }
	
	public int getID() { return id;}
	
	public Teams getTeam(Player player) { return teams.get(player.getUniqueId()); }
	
	public HashMap<UUID, Kit> getKits() { return kits;}
	
	public List<UUID> getPlayers() {return players;}
	
	public GameState getState() { return state; }
	
	public boolean getjoinstate() { return canJoin; }
	
	public  Location getSpawn()  { return spawn; }
	
	public void setState(GameState state) {this.state = state; }
	
	public void removeKit(UUID uuid) {
		if (kits.containsKey(uuid)) {
			kits.get(uuid).remove();
			kits.remove(uuid);
		}
	}
	
	public void setKit(UUID uuid, KitType type) {

		
		removeKit(uuid);
		
		if (type.equals(KitType.FIGHTER)) {
			kits.put(uuid, new Fighter(uuid));
		} else if (type.equals(KitType.MINER)) {
			kits.put(uuid, new Miner(uuid));
		}
	}
	
	public void setJoinState(boolean state) { this.canJoin = state;}
	
	public void setTeam(Player player, Teams team) {
		removeTeam(player);
		teams.put(player.getUniqueId(), team);
	}
	
	public void removeTeam(Player player) {
		if (teams.containsKey(player.getUniqueId())) {
			teams.remove(player.getUniqueId());
		}
	}
	
	public int getTeamCount(Teams team) {
		
		int amount = 0;
		
		for (Teams t : Teams.values()) {
			if (t.equals(team)) {
				amount ++;
			}
		}
		
		return amount;
		
	}
	
	

}
