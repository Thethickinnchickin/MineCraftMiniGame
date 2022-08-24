package com.matthew.main.instance;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.manager.ConfigManager;
import org.bukkit.Bukkit;
import com.matthew.main.instance.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;

    private GameState state;
    private Main main;

    private List<UUID> players;
    private Countdown countdown;
    private Game game;


    public Arena(Main main, int id, Location spawn) {
        this.id = id;
        this.spawn = spawn;
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(main,this);
        this.game = new Game(this);
        this.main = main;
    }

    /* Game */

    public void start() {
        game.start();
    }

    public void reset(boolean kickPlayers) {
        if (kickPlayers) {
            Location loc = ConfigManager.getLobbySpawn();
            for (UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(loc);
            }
            players.clear();
        }
        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(main, this);
        game = new Game(this);
    }

    /* Arena Tools */

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    /* Players */

    //Adding Player to Arena Players List
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()) {
            countdown.start();
        }
    }
    //Removing Player from Arena Players List
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "There is not enough players. Countdown stopped.");
            reset(false);
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "The game has ended as too many players left");
            reset(false);
        }
    }

    //Getting Id of Arena
    public int getId() {return id; }

    //Getting Current Game State of Arena
    public  GameState getState() { return state; }

    //Getting List of Player that are currently in the Arena
    public List<UUID> getPlayers() { return players; }

    //Setting Current Game State to param state
    public void setState(GameState state) { this.state = state; }

    //Getting Current Game
    public Game getGame() { return game; }
}
