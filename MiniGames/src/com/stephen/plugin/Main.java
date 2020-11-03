package com.stephen.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		
		Main.instance = this;
		
		new Config(this);
		
		new Manager();
		
		getCommand("arena").setExecutor(new ArenaCommand());
		
		Bukkit.getPluginManager().registerEvents(new GameListener(), this);
		
		System.out.println("Plugin Enabled!!!");
		
		
		

		
		
	}
	
	public static Main getInstance() { return instance; }

}
