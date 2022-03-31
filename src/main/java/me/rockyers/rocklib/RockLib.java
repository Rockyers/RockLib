package me.rockyers.rocklib;

import lombok.Getter;
import me.rockyers.rocklib.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * RockLib is the main JavaPlugin class of this whole thing. Only used if the .jar is used as a plugin on a CraftBukkit/Spigot/Paper server
 *
 * @author Rockyers
 * @since 1.0.0
 */
public final class RockLib extends JavaPlugin {

    /**
     * The instance getter, to avoid static abuse
     */
    @Getter private static RockLib instance;

    /**
     * If RockLib is used as a plugin
     */
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b&lRockLib has been enabled!"));
    }

    /**
     * Currently unused
     */
    @Override
    public void onDisable() {

    }
}
