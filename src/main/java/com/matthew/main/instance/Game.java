package com.matthew.main.instance;

import com.matthew.main.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;
    private Score website;
    private BossBar bossBar;

    public Game(Arena arena) {
        this.arena = arena;
        points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "GAME HAS STARTED!" +
                " Your objective is to break 20 blocks in the fastest time. Good luck!");

        for (UUID uuid : arena.getPlayers()) {

            Player player = Bukkit.getPlayer(uuid);

            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective obj = board.registerNewObjective("Test", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName(ChatColor.GREEN.toString()
                    + ChatColor.BOLD + "PLAYERS POINTS");
//

            if(player != null) {
                this.bossBar = Bukkit.createBossBar(
                        ChatColor.LIGHT_PURPLE + "Block Breakers"
                                 + ChatColor.GOLD + "-GOLD: 3 points"  +
                                 ChatColor.GRAY + "-SILVER: 2 points"  +
                                 ChatColor.WHITE +  "-ELSE 1 points",
                        BarColor.BLUE,
                        BarStyle.SEGMENTED_10
                );

                this.bossBar.addPlayer(player);


            }



            this.website = obj.getScore(ChatColor.BLUE + "Name: " + player.getName());
            this.website.setScore(0);



            player.setScoreboard(board);

            points.put(uuid, 0);
        }
    }

    public void addPoint(Player player, String pointType) {
        int playerPoints;
        switch (pointType) {
            case "gold":
                playerPoints = points.get(player.getUniqueId()) + 3;
                player.sendMessage(ChatColor.GOLD + "+3 POINT!");
                points.replace(player.getUniqueId(), playerPoints);
                break;
            case "silver":
                playerPoints = points.get(player.getUniqueId()) + 2;
                player.sendMessage(ChatColor.DARK_AQUA + "+2 POINT!");
                points.replace(player.getUniqueId(), playerPoints);
                break;
            default:
                playerPoints = points.get(player.getUniqueId()) + 1;
                player.sendMessage(ChatColor.DARK_AQUA + "+1 POINT!");
                points.replace(player.getUniqueId(), playerPoints);
                break;

        }

        this.website.setScore(points.get(player.getUniqueId()));


        if (playerPoints >= 20) {
            arena.sendMessage(ChatColor.GOLD + player.getName() + "Has WON! Thanks for playing :)");
            for(UUID playerToReset : arena.getPlayers()) {
                Player player1 = Bukkit.getPlayer(playerToReset);
                player1.resetTitle();
                player1.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                this.bossBar.removeAll();
            }
            arena.reset(true);
            return;
        }

    }

    public int getPoints(Player player) {
        return points.get(player.getUniqueId());
    }

}
