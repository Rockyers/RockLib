package me.rockyers.rocklib.objects;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface RockRunnable {
    public abstract void run(Player thePlayer);
}
