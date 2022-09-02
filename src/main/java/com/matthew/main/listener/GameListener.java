package com.matthew.main.listener;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.instance.Arena;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class GameListener implements Listener {

    private Main main;

    public GameListener(Main main) {
        this.main = main;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        System.out.println(e.getBlock().getBiome());
        Arena arena = main.getArenaManager().getArena(e.getPlayer());
        if (arena != null) {
            if (arena.getState().equals(GameState.LIVE)) {
                if(e.getBlock().getType() == Material.SAND) {
                    arena.getGame().addPoint(e.getPlayer(), "gold");
                } else if (e.getBlock().getType() == Material.DIRT) {
                    arena.getGame().addPoint(e.getPlayer(), "silver");
                } else if(e.getBlock().getType() != Material.DIAMOND_BLOCK
                || e.getBlock().getType() != Material.GOLD_BLOCK) {
                    arena.getGame().addPoint(e.getPlayer(), "other");
                }

            }
        }
    }
}
