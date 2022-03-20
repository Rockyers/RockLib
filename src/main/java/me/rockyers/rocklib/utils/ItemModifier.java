package me.rockyers.rocklib.utils;

import org.bukkit.inventory.ItemStack;

public class ItemModifier {
    ItemStack item;
    Gui gui;

    public ItemModifier (ItemStack item, Gui gui) {
        this.gui = gui;
        this.item = item;
    }

    public ItemModifier setClickFunctions(ItemStack itemStack, Runnable leftClickFnc, Runnable rightClickFnc, Runnable middleClickFnc) {
        gui.getClickFunctions().replace(itemStack, new Runnable[]{leftClickFnc, rightClickFnc, middleClickFnc});
        return this;
    }

    public ItemModifier setClickFunctions(ItemStack itemStack, Runnable leftClickFnc, Runnable rightClickFnc) {
        gui.getClickFunctions().replace(itemStack, new Runnable[]{leftClickFnc, rightClickFnc, null});
        return this;
    }

    public ItemModifier setLeftClickFunction(ItemStack itemStack, Runnable leftClickFnc) {
        gui.getClickFunctions().replace(itemStack, new Runnable[]{leftClickFnc, null, null});
        return this;
    }

    public ItemModifier setRightClickFunction(ItemStack itemStack, Runnable rightClickFnc) {
        gui.getClickFunctions().replace(itemStack, new Runnable[]{null, rightClickFnc, null});
        return this;
    }

    public ItemModifier setMiddleClickFunction(ItemStack itemStack, Runnable middleClickFnc) {
        gui.getClickFunctions().replace(itemStack, new Runnable[]{null, null, middleClickFnc});
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
