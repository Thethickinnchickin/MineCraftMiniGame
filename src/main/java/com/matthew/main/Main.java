package com.matthew.main;

import com.matthew.main.command.ArenaCommand;
import com.matthew.main.event.BlockBreakersDoorEvent;
import com.matthew.main.listener.ConnectListener;
import com.matthew.main.listener.GameListener;
import com.matthew.main.listener.WorldLoadEvent;
import com.matthew.main.manager.ArenaManager;
import com.matthew.main.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setupConfig(this);
        this.arenaManager = new ArenaManager(this);


        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakersDoorEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new WorldLoadEvent(this), this);
        getCommand("arena").setExecutor(new ArenaCommand(this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager() { return this.arenaManager; }
}
