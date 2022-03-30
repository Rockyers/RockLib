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

    public ItemModifier setClickFunction(HashMap<ClickType, RockRunnable> clickTypeMap) {
        gui.getClickFunctions().replace(item, clickTypeMap);
        return this;
    }

    public ItemModifier setClickFunction(RockRunnable action, ClickType... clickTypes) {
        HashMap<ClickType, RockRunnable> clickTypeMap = new HashMap<>();
        for (ClickType clickType : clickTypes) clickTypeMap.put(clickType, action);
        gui.getClickFunctions().replace(item, clickTypeMap);
        return this;
    }

    public ItemModifier setPermission(String permission) {
        gui.getItemPermissions().replace(item, permission);
        return this;
    }

    public ItemModifier modifyItem(ItemStack item) {
        return new ItemModifier(item, gui);
    }
}
