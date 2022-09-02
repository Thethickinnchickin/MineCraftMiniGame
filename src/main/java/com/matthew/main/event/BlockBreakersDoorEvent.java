package com.matthew.main.event;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockBreakersDoorEvent implements Listener {

    private Main main;

    public BlockBreakersDoorEvent(Main main) {
        this.main = main;
    }


    @EventHandler
    public void onDoorOpen(PlayerInteractEvent e) {
        if(main.getArenaManager().getArena(0).getCanJoin()) {

            Block block = e.getClickedBlock();
            Action action = e.getAction();
            if((action == Action.RIGHT_CLICK_BLOCK) ||
                    (action == Action.LEFT_CLICK_BLOCK)) {
                if(block.getType() == Material.WARPED_DOOR) {
                    if (this.main.getArenaManager().getArena(0) != null) {
                        this.main.getArenaManager().getArena(0).addPlayer(e.getPlayer());
                        this.main.getArenaManager().getArena(0).sendMessage(ChatColor.RED + "You have join Block Breakers");
                    }

                }
            }
        }

//        if (arena != null) {
//            if (arena.getState().equals(GameState.LIVE)) {
//                if(e.getBlock().getType() == Material.SAND) {
//                    arena.getGame().addPoint(e.getPlayer(), "gold");
//                } else if (e.getBlock().getType() == Material.DIRT) {
//                    arena.getGame().addPoint(e.getPlayer(), "silver");
//                } else {
//                    arena.getGame().addPoint(e.getPlayer(), "other");
//                }
//
//            }
//        }
    }



}
