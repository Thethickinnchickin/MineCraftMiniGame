package com.stephen.plugin;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;

public class Countdown extends BukkitRunnable{
	
	private Arena arena;
	private int seconds; 
	
	public Countdown(Arena arena) {
		this.arena = arena; 
		this.seconds = Config.getCountdownSeconds();
	}
	
	public void begin() {
		arena.setState(GameState.COUNTDOWN);
		this.runTaskTimer(Main.getInstance(), 0, 20);
		
		arena.updateSign(ChatColor.WHITE + "ARENA " + arena.getID(), ChatColor.BLUE + "COUNTDOWN", " ", ChatColor.GRAY + "Click to join");
		
	}

	@Override
	public void run() {
		if (seconds == 0) {
			cancel();
			arena.start();
			return;
		}
		
		if(seconds % 30 == 0 || seconds <= 10) {
			if(seconds == 1) {
				arena.sendMessage(ChatColor.AQUA + "The Game Will Start In 1 Second!");
			} else {
				arena.sendMessage(ChatColor.AQUA + "The Game Will Start In " + seconds + " Seconds!");
			}
		}
		
		if (arena.getPlayers().size() < Config.getRequiredlayers()) {
			cancel();
			arena.setState(GameState.COUNTDOWN);
			arena.sendMessage(ChatColor.RED + "Not enough players to start the game!!! / Countdown Ending");
			arena.updateSign(ChatColor.WHITE + "ARENA " + arena.getID(), ChatColor.BLUE + "COUNTDOWN", " ", ChatColor.GRAY + "Click to join");
			return;
		}
		
		
		seconds--;
		
	}
	

}
