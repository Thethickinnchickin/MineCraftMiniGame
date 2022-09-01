package com.matthew.main.command;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.instance.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class ArenaCommand implements CommandExecutor {

    private Main main;

    public ArenaCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.GREEN + "These are the available arenas:");
                for (Arena arena : main.getArenaManager().getArenas()) {
                    player.sendMessage(ChatColor.GREEN + "- " + arena.getId() + "("  + arena.getState().name() + ")");
                }

            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")){
                Arena arena = main.getArenaManager().getArena(player);
                if (arena != null) {
                    player.sendMessage(ChatColor.RED + "You left the arena");
                    player.getInventory().remove(Material.IRON_PICKAXE);
                    main.getArenaManager().getArena(player).getGame().getBossBar().removeAll();
                    player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                    arena.removePlayer(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in an arena");
                }

            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                if(main.getArenaManager().getArena(player) != null) {
                    player.sendMessage(ChatColor.RED + "You are already playing in an arena");
                    return false;
                }

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "You specified an invalid arena ID");
                    return false;
                }

                if (id >= 0 && id < main.getArenaManager().getArenas().size()) {
                    Arena arena = main.getArenaManager().getArena(id);
                    if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                        player.sendMessage(ChatColor.GREEN + "You are now playing in Arena " + id + ".");
                        arena.addPlayer(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You cannot join this game right now!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You specified an Invaliod arena ID");
                }

            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage! These are the options:");
                player.sendMessage(ChatColor.RED + " /arena list");
                player.sendMessage(ChatColor.RED + " /arena leave");
                player.sendMessage(ChatColor.RED + " /arena join <id>");
            }
        }

        return false;
    }
}
