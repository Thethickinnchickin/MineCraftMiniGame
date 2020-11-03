package com.stephen.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.World;

public class Config {
	
	private static Main main;
	
	public Config(Main main) {
		
		this.main = main;
		
		main.getConfig().options().copyDefaults();
		main.saveDefaultConfig();
		
	}
	
	public static int getRequiredlayers() { return main.getConfig().getInt("required-players");}
	 
	public static int getCountdownSeconds() {  return main.getConfig().getInt("countdown-seconds");}
	
	public static Location getLobbySpawn() {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("lobby-spawn.world")),
				main.getConfig().getDouble("lobby-spawn.x"),
				main.getConfig().getDouble("lobby-spawn.y"),
				main.getConfig().getDouble("lobby-spawn.z"),
				main.getConfig().getInt("lobby-spawn.yaw"),
				main.getConfig().getInt("lobby-spawn.pitch"));
		
		
	}
	
	public static Location getArenaSpawn(int id) {
		
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("arenas." + id + ".spawn.world")));
		world.setAutoSave(false);
		return new Location (
				world,
				main.getConfig().getDouble("arenas." + id + ".spawn.x"),
				main.getConfig().getDouble("arenas." + id + ".spawn.y"),
				main.getConfig().getDouble("arenas." + id + ".spawn.z"),
				main.getConfig().getInt("arenas." + id + ".spawn.yaw"),
				main.getConfig().getInt("arenas." + id + ".spawn.pitch")
				);

		
				
		
				
		
		
		
	}
	
	public static Location getArenaSignSpawn(int id) {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("arenas." + id + ".sign.world")),
				main.getConfig().getDouble("arenas." + id + ".sign.x"),
				main.getConfig().getDouble("arenas." + id + ".sign.y"),
				main.getConfig().getDouble("arenas." + id + ".sign.z")
				);

	}
	
	public static int getArenaAmount() {
		return main.getConfig().getConfigurationSection("arenas.").getKeys(false).size();
	}
	
	public static int isSign(Location location) {
		
		for (int i = 1; i < getArenaAmount(); i++) {
			if (getArenaSignSpawn(i).equals(location)) {
				return i;
			}
		}
		return -1;
		
	}
	

}
