package me.rockyers.rocklib.utils;

import me.rockyers.rocklib.RockLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Gui implements Listener {

    // ------------------------------------- //
    // Instance variables.

    private Inventory inventory;
    private String name;
    private int rows = 3;
    private ItemStack[] items;
    private HashMap<ItemStack, Runnable[]> clickFunctions = new HashMap<>();
    private HashMap<ItemStack, String> itemPermissions = new HashMap<>();

    private boolean takeItemOnNoPerm, isIntractable, useCustomListener = false;
    private boolean closeInventoryOnNoPerm, soundOnNoPerm = true;

    private String noPermError = "&cNo Permission!";


    // ------------------------------------- //
    // Constructors.

    public Gui(String guiName, int rows, ItemStack[] items, HashMap<ItemStack, Runnable[]> clickFunctions, HashMap<ItemStack, String> itemPermissions) {
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
        this.items = items;
        this.clickFunctions = clickFunctions;
        this.itemPermissions = itemPermissions;
        this.rows = rows;
        this.name = guiName;
    }

    public Gui(String guiName, int rows, ItemStack[] items) {
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
        this.items = items;
        this.rows = rows;
        this.name = guiName;
    }

    public Gui(String guiName, ItemStack[] items) {
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
        this.items = items;
        this.name = guiName;
    }

    public Gui(String guiName, int rows) {
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
        items = new ItemStack[rows * 9];
        this.rows = rows;
        this.name = guiName;
    }

    public Gui(String guiName) {
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
        items = new ItemStack[rows * 9];
        this.name = guiName;
    }


    // ------------------------------------- //
    // Helper methods.

    // Setters.
    public Gui setItem(ItemStack item, int slot, Runnable leftClickFnc, Runnable rightClickFnc, Runnable middleClickFnc, String permission) {
        items[slot] = item;
        clickFunctions.put(item, new Runnable[]{leftClickFnc, rightClickFnc, middleClickFnc});
        itemPermissions.put(item, permission);
        return this;
    }
    public Gui setItem(ItemStack item, int slot, Runnable leftClickFnc, Runnable rightClickFnc, Runnable middleClickFnc) {
        items[slot] = item;
        clickFunctions.put(item, new Runnable[]{leftClickFnc, rightClickFnc, middleClickFnc});
        return this;
    }
    public Gui setItem(ItemStack item, int slot, Runnable leftClickFnc, Runnable rightClickFnc, String permission) {
        items[slot] = item;
        clickFunctions.put(item, new Runnable[]{leftClickFnc, rightClickFnc, null});
        itemPermissions.put(item, permission);
        return this;
    }
    public Gui setItem(ItemStack item, int slot, Runnable leftClickFnc, Runnable rightClickFnc) {
        items[slot] = item;
        clickFunctions.put(item, new Runnable[]{leftClickFnc, rightClickFnc, null});
        return this;
    }
    public Gui setItem(ItemStack item, int slot, Runnable leftClickFnc, String permission) {
        items[slot] = item;
        clickFunctions.put(item, new Runnable[]{leftClickFnc, null, null});
        itemPermissions.put(item, permission);
        return this;
    }
    public Gui setItem(ItemStack item, int slot, Runnable leftClickFnc) {
        items[slot] = item;
        clickFunctions.put(item, new Runnable[]{leftClickFnc, null, null});
        return this;
    }
    public Gui setItem(ItemStack item, int slot, String permission) {
        items[slot] = item;
        itemPermissions.put(item, permission);
        return this;
    }
    public Gui setItem(ItemStack item, int slot) {
        items[slot] = item;
        return this;
    }

    public ItemModifier modifyItem(ItemStack item) {
        return new ItemModifier(item, this);
    }

    public Gui setItems(ItemStack[] items, HashMap<ItemStack, Runnable[]> clickFunctions, HashMap<ItemStack, String> itemPermissions) {
        this.items = items;
        this.itemPermissions = itemPermissions;
        this.clickFunctions = clickFunctions;
        return this;
    }
    public Gui setItems(ItemStack[] items, HashMap<ItemStack, Runnable[]> clickFunctions) {
        this.items = items;
        this.clickFunctions = clickFunctions;
        return this;
    }
    public Gui setItems(ItemStack[] items) {
        this.items = items;
        return this;
    }

    public void closeInventoryOnNoPerm(boolean closeInventoryOnNoPerm) {
        this.closeInventoryOnNoPerm = closeInventoryOnNoPerm;
    }

    public void intractable(boolean intractable) {
        isIntractable = intractable;
    }

    public void setSoundOnNoPerm(boolean soundOnNoPerm) {
        this.soundOnNoPerm = soundOnNoPerm;
    }

    public void takeItemOnNoPerm(boolean takeItemOnNoPerm) {
        this.takeItemOnNoPerm = takeItemOnNoPerm;
    }

    public void useCustomListener(boolean useCustomListener) {
        this.useCustomListener = useCustomListener;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setNoPermError(String noPermError) {
        this.noPermError = noPermError;
    }

    // Getters.
    public ItemStack getItem(int itemSlot) {
        return items[itemSlot];
    }

    public ItemStack[] getItems() {
        return items;
    }

    public boolean isCloseInventoryOnNoPerm() {
        return closeInventoryOnNoPerm;
    }

    public boolean isIntractable() {
        return isIntractable;
    }

    public boolean isSoundOnNoPerm() {
        return soundOnNoPerm;
    }

    public boolean isTakeItemOnNoPerm() {
        return takeItemOnNoPerm;
    }

    public boolean isUseCustomListener() {
        return useCustomListener;
    }

    public String getName() {
        return name;
    }

    public String getNoPermError() {
        return noPermError;
    }

    public int getRows() {
        return rows;
    }

    public Inventory toInventory() {
        return inventory;
    }

    // ------------------------------------- //
    // Deprecated methods.
    //
    // These methods are for very advanced usage, and you should not have to use them!

    // @Deprecated
    public void refreshGuiContents() {
        this.inventory.setContents(this.items);
    }

    // @Deprecated
    public HashMap<ItemStack, Runnable[]> getClickFunctions() {
        return clickFunctions;
    }

    // @Deprecated
    public void setClickFunctions(HashMap<ItemStack, Runnable[]> clickFunctions) {
        this.clickFunctions = clickFunctions;
    }

    // @Deprecated
    public HashMap<ItemStack, String> getItemPermissions() {
        return itemPermissions;
    }

    // @Deprecated
    public void setItemPermissions(HashMap<ItemStack, String> itemPermission) {
        this.itemPermissions = itemPermission;
    }


    // ------------------------------------- //
    // More helper methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gui)) return false;
        Gui guiTest = (Gui) o;
        return getRows() == guiTest.getRows() && takeItemOnNoPerm == guiTest.takeItemOnNoPerm && isCloseInventoryOnNoPerm() == guiTest.isCloseInventoryOnNoPerm() && isIntractable() == guiTest.isIntractable() && isSoundOnNoPerm() == guiTest.isSoundOnNoPerm() && inventory.equals(guiTest.inventory) && name.equals(guiTest.name) && Arrays.equals(getItems(), guiTest.getItems()) && getClickFunctions().equals(guiTest.getClickFunctions()) && getItemPermissions().equals(guiTest.getItemPermissions()) && Objects.equals(getNoPermError(), guiTest.getNoPermError());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(inventory, name, getRows(), getClickFunctions(), getItemPermissions(), isIntractable());
        result = 31 * result + Arrays.hashCode(getItems());
        return result;
    }

    public void openGui(Player player) {
        this.inventory = Bukkit.createInventory(player, this.rows * 9, ChatColor.translateAlternateColorCodes('&', this.name));
        this.inventory.setContents(this.items);
        player.openInventory(this.inventory);
    }

    /**
     * For debug purposes could send gui.toString() to player
     */

    @Override
    public String toString() {
        return "Gui{" +
                "gui=" + inventory +
                ", guiName='" + name + '\'' +
                ", rows=" + rows +
                ", items=" + Arrays.toString(items) +
                ", clickFunctions=" + clickFunctions +
                ", itemPermission=" + itemPermissions +
                ", isIntractable=" + isIntractable +
                '}';
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent ev) {
        if (useCustomListener) return;
        Player whoClicked = (Player) ev.getWhoClicked();
        ItemStack itemClicked = ev.getCurrentItem();
        Inventory inventoryClicked = ev.getClickedInventory();
        ClickType clickType = ev.getClick();

        if (inventoryClicked == null || !inventoryClicked.equals(this.toInventory())) return;
        ev.setCancelled(!isIntractable);
        if ((!itemPermissions.containsKey(itemClicked) || whoClicked.hasPermission(itemPermissions.get(itemClicked))) && clickFunctions.containsKey(itemClicked)) {
            int index = switch (clickType) {
                case CREATIVE, LEFT -> 0;
                case SHIFT_RIGHT, RIGHT -> 1;
                case MIDDLE -> 2;
                default -> 0;
            };
            clickFunctions.get(itemClicked)[index].run();
        } else {
            ev.setCancelled(!takeItemOnNoPerm);
            if (closeInventoryOnNoPerm) whoClicked.closeInventory();
            if (soundOnNoPerm) whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            if (!noPermError.isEmpty()) whoClicked.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermError));
        }
    }
}
