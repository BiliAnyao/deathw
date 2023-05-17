package com.lm;


import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.attribute.Attribute;

import org.bukkit.scheduler.BukkitRunnable;

public final class Deathw extends JavaPlugin {

    private float lastHunger = 0;
    private final float maxHunger = 20;
    private final float maxSaturation = 20;

    @Override
    public void onEnable() {
        getLogger().info("Stats plugin enabled!");


        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : getServer().getOnlinePlayers()) {
                    syncStats(player);
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Stats plugin disabled!");
    }

    private void syncStats(Player player){
        float health = (float) player.getHealth();
        float hunger = player.getFoodLevel();
        float saturation = player.getSaturation();


        if (hunger < lastHunger) {
            float damage = lastHunger - hunger;
            player.damage(damage);
        }

        else if (health < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            float healAmount = hunger - lastHunger;
            player.setHealth(Math.min(health + healAmount, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
        }


        if (hunger >= maxHunger && saturation < maxSaturation) {
            float satIncrease = hunger - lastHunger;
            player.setSaturation(Math.min(saturation + satIncrease, maxSaturation));
        }

        lastHunger = hunger;
    }
}