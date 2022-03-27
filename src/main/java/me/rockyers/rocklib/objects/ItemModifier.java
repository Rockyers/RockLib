package me.rockyers.rocklib.objects;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemModifier {
    ItemStack item;
    Gui gui;

    public ItemModifier (ItemStack item, Gui gui) {
        this.gui = gui;
        this.item = item;
    }

    public ItemModifier setClickFunction(ItemStack itemStack, HashMap<ClickType, RockRunnable> clickTypeMap) {
        gui.getClickFunctions().replace(itemStack, clickTypeMap);
        return this;
    }

    public ItemModifier setPermission(ItemStack itemStack, String permission) {
        gui.getItemPermissions().replace(itemStack, permission);
        return this;
    }

    public ItemModifier modifyItem(ItemStack item) {
        return new ItemModifier(item, gui);
    }
}
