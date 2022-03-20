package me.rockyers.rocklib;

import org.bukkit.plugin.java.JavaPlugin;

public final class RockLib extends JavaPlugin {

    private static RockLib instance;
    public static RockLib getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
