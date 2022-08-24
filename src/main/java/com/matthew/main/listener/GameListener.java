package com.matthew.main.listener;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.instance.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private Main main;

    public GameListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Arena arena = main.getArenaManager().getArena(e.getPlayer());
        if (arena != null) {
            if (arena.getState().equals(GameState.LIVE)) {
                arena.getGame().addPoint(e.getPlayer());
            }
        }
    }
}
