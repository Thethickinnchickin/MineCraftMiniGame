package com.matthew.main.instance;

import com.matthew.main.GameState;
import com.matthew.main.Main;
import com.matthew.main.listener.GameListener;
import com.matthew.main.manager.ConfigManager;
import org.bukkit.*;
import com.matthew.main.instance.Game;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private Location spawn;
    private Location lobbySpawn;
    private FileConfiguration config;

    private GameState state;
    private Main main;


    private List<UUID> players;
    private Countdown countdown;
    private Game game;
    private boolean canJoin;


    public Arena(Main main, int id, Location spawn) {
        this.id = id;
        this.spawn = spawn;
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(main,this);
        this.game = new Game(this);
        this.main = main;
        this.config = main.getConfig();
        this.lobbySpawn = ConfigManager.getBlockBreakersLobbySpawn();
        this.canJoin = true;
    }

    /* Game */

    public void start() {
        game.start();
    }

    public void reset() {

        if (state == GameState.LIVE) {
            this.canJoin = false;
            Location loc = ConfigManager.getLobbySpawn();
            for (UUID uuid : players) {
                Bukkit.getPlayer(uuid).teleport(loc);
            }
            players.clear();

            String worldName = spawn.getWorld().getName();
            System.out.println("start");
            Bukkit.unloadWorld(spawn.getWorld().getName(), false);
            System.out.println("finish");
            FileConfiguration config = main.getConfig();
            World world = new WorldCreator(worldName).createWorld();
            world.setAutoSave(false);

        }


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
        player.teleport(lobbySpawn);
        System.out.println(ConfigManager.getRequiredPlayers());

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
            reset();
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "The game has ended as too many players left");
            reset();
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

    //Getting world of Arena
    public World getWorld() { return spawn.getWorld(); }

    //Toggle canJoin variable
    public void setCanJoin(boolean canJoinNew) { this.canJoin = canJoinNew; }

    public boolean getCanJoin() { return this.canJoin; }


    public Location getArenaSpawn() { return this.spawn;}
}
