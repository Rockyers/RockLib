package me.rockyers.rocklib.TEST;

import me.rockyers.rocklib.RockLib;
import me.rockyers.rocklib.abstracts.SubCommand;
import me.rockyers.rocklib.constructers.ItemConstructer;
import me.rockyers.rocklib.objects.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class SubTest extends SubCommand {

    @Override
    public String getName() {
        return "subtest";
    }

    @Override
    public String getDescription() {
        return "This is a test!";
    }

    @Override
    public String getSyntax() {
        return "/test subtest";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String getNoPermMessage() {
        return null;
    }

    @Override
    public void perform(@NotNull Player player, String[] args) {
        Gui gui = new Gui("&cThis is a test!", InventoryType.ANVIL, RockLib.getInstance());
        gui.setItem(new ItemConstructer(Material.PLAYER_HEAD).setName("&c" + player.getDisplayName()).setHeadSkin(player.getUniqueId()).setLore("&7Gamer").create(),
                0,
                new HashMap<>());
        gui.openGui(player);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
