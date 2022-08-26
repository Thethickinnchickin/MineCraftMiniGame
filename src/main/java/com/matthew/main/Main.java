package com.matthew.main;

import com.matthew.main.command.ArenaCommand;
import com.matthew.main.listener.ConnectListener;
import com.matthew.main.listener.GameListener;
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
        System.out.println("you peice of shit");
        getCommand("arena").setExecutor(new ArenaCommand(this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager() { return this.arenaManager; }
}
