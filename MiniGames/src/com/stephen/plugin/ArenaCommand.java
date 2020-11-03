package com.stephen.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stephen.plugin.kits.KitsGUI;

import org.bukkit.ChatColor;

public class ArenaCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 1 && args[0].equalsIgnoreCase("team")) {
				if (Manager.isPlaying(player)) {
					if (Manager.getArena(player).getState().equals(GameState.COUNTDOWN) || Manager.getArena(player).getState().equals(GameState.RECRUITING)) {
						new TeamsGUI(player);
					} else {
						player.sendMessage(ChatColor.RED + "You cannot chose a team while the game is Live!!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You aren't in an arena");
				}
			} else if(args.length == 1 && args[0].equalsIgnoreCase("kit")) {
				
				if (Manager.isPlaying(player)) {
					if (Manager.getArena(player).getState().equals(GameState.RECRUITING) || 
							Manager.getArena(player).getState().equals(GameState.COUNTDOWN)) {
						
						new KitsGUI(player);
						
	
					} else {
						player.sendMessage(ChatColor.RED + " You can't join because this game is already live!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are not in a arena!");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.GREEN + "These are the valid arenas:");
		
				for (Arena arena : Manager.getArenas()) {
					player.sendMessage(ChatColor.GREEN + "- " + arena.getID());
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if (Manager.isPlaying(player)) {
					Manager.getArena(player).removePlayer(player);
				} else {
					player.sendMessage(ChatColor.RED + "You are not in a Arena!");
				}
			} else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
				try {
					int id = Integer.parseInt(args[1]);
						
					if (id >= 0 && id <= (Config.getArenaAmount() - 1)) {
						if (Manager.isRecruiting(id)) {
							
							if(Manager.getArena(id).getjoinstate()) {
								Manager.getArena(id).addPlayer(player);
								
								player.sendMessage(ChatColor.GREEN + "You have joined arena" + id + "!");
							} else {
								player.sendMessage(ChatColor.RED + "You cannot join while arena is loading");
							}
						} else {
							player.sendMessage(ChatColor.RED + "You cannot join this game right now!");
						}
					} else {
						player.sendMessage(ChatColor.RED + "invalid arena! See /arena lisr for available options");
					}
					
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid Arena! See arena list for all available arenas");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid usage! these are the options:");
				player.sendMessage(ChatColor.RED + "/arena list");
				player.sendMessage(ChatColor.RED + "/arena join [id]");
				player.sendMessage(ChatColor.RED + "/arena leave");
			}
		} else {
			System.out.println("You cannot use this command in console!");
		}
		return false;
	}

}
