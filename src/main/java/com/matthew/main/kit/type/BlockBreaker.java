package com.matthew.main.kit.type;

import com.matthew.main.Main;
import com.matthew.main.kit.Kit;
import com.matthew.main.kit.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class BlockBreaker extends Kit {

    public BlockBreaker(Main main, UUID uuid) {
        super(main, KitType.BLOCKBREAKER, uuid);
    }


    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.SPEED, 100 , 3));
    }
}
