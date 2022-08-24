package com.matthew.main.instance;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Main main;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(Main main, Arena arena) {
        this.main = main;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start() {
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(main,0,20);
    }

    @Override
    public void run() {
        if(countdownSeconds == 0) {
            cancel();
            //arena state
            return;
        }

        if(countdownSeconds <= 10 || countdownSeconds % 15 == 0) {
            arena.sendMessage(ChatColor.GREEN + "Game will start in "
                    + countdownSeconds + " seconds" + (countdownSeconds == 1 ? "" : "s") + ".");
        }

        arena.sendTitle(ChatColor.GREEN.toString() + countdownSeconds
                + " second" + (countdownSeconds == 1 ? "" : "s"), ChatColor.GRAY + "unit game starts");

        countdownSeconds--;
    }
}
