package com.matthew.main.listener;

import com.matthew.main.Main;
import com.matthew.main.instance.Arena;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldLoadEvent implements Listener {

    private Main main;

    public WorldLoadEvent(Main main) {
        this.main = main;
    }


    @EventHandler
    public void onWorldLoad(org.bukkit.event.world.WorldLoadEvent e) {
        System.out.println("HEY");
        Arena arena = main.getArenaManager().getArena(e.getWorld());
        if (arena != null) {
            System.out.println("HEY");
            arena.setCanJoin(true);
        }
    }
}
