package me.rockyers.rocklib.TEST;

import me.rockyers.rocklib.RockLib;
import me.rockyers.rocklib.abstracts.SubCommand;
import me.rockyers.rocklib.objects.Item;
import me.rockyers.rocklib.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.List;

public class SubTestTwo extends SubCommand {

    @Override
    public String getName() {
        return "subtesttwo";
    }

    @Override
    public String getDescription() {
        return "gives item!";
    }

    @Override
    public String getSyntax() {
        return "/test subtesttwo";
    }

    @Override
    public String getPermission() {
        return "is.gamer";
    }

    @Override
    public String getNoPermMessage() {
        return "No Permission";
    }

    @Override
    public void perform(Player player, String[] args) {
        Item item = new Item("&cEpic!", Material.STICK, RockLib.getInstance()).setLore("&7Epic Lore :O").setFunction(() -> player.sendMessage(CC.translate("&cEpic")), Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
        player.getInventory().addItem(item.toItemStack());
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
