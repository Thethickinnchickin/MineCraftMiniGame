package com.matthew.main.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum KitType {

    BLOCKBREAKER(ChatColor.GOLD + "BLOCKBREAKER", Material.IRON_PICKAXE,
            "Best for breaking blocks");

    private String display, description;
    private Material material;

    KitType(String display, Material material, String description) {
        this.display = display;
        this.material = material;
        this.description = description;
    }

    public String getDisplay() { return display; }

    public Material getMaterial() {return material; }

    public String getDescription() {return description; }
}
