package me.rockyers.rocklib;

import me.rockyers.rocklib.TEST.SubTest;
import me.rockyers.rocklib.TEST.SubTestTwo;
import me.rockyers.rocklib.objects.CommandManager;
import me.rockyers.rocklib.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RockLib extends JavaPlugin {

    private static RockLib instance;
    public static RockLib getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b&lRockLib has been enabled!"));
        CommandManager manager = new CommandManager("test", this);
        manager.addSubCommand(new SubTestTwo()).addSubCommand(new SubTest());
    }

    @Override
    public void onDisable() {

    }
}
