package com.matthew.main.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public class BlockBreakScoreBoard {

    private Player player;

    public BlockBreakScoreBoard(UUID uuid) {
        this.player = Bukkit.getPlayer(uuid);
    }

    public void createScorboard() {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("Test", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Test Board!");

        Score website = obj.getScore(ChatColor.YELLOW + "www.testwebsite.com");
        website.setScore(1);

        Score space = obj.getScore(" ");
        space.setScore(2);

        Score name = obj.getScore(ChatColor.BLUE + "Name: " + this.player.getName());
        name.setScore(3);

        this.player.setScoreboard(board);
    }
}
