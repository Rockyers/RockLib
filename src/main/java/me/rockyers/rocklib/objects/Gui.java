package me.rockyers.rocklib.objects;

import lombok.Getter;
import me.rockyers.rocklib.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Gui implements Listener {

    /**
     * Interface Variables
     */
    private Inventory inventory;
    @Getter private InventoryType type = InventoryType.CHEST;
    @Getter private String name;
    @Getter private int rows = 3;
    @Getter private ItemStack[] items;
    private HashMap<ItemStack, HashMap<ClickType, RockRunnable>> clickFunctions = new HashMap<>();
    private HashMap<ItemStack, String> itemPermissions = new HashMap<>();
    @Getter private ItemStack filler = null;
    @Getter private ItemStack outLineItem = null;

    @Getter private boolean takeItemOnNoPerm, isIntractable, useCustomListener = false;
    @Getter private boolean closeInventoryOnNoPerm, soundOnNoPerm = true;

    @Getter private String noPermError = "&cNo Permission!";

    @Getter private JavaPlugin plugin;

    /**
     * Constructors
     */
    public Gui(@NotNull Gui gui, @NotNull JavaPlugin plugin) {
        this.filler = gui.getFiller();
        this.clickFunctions = gui.getClickFunctions();
        this.closeInventoryOnNoPerm = gui.isCloseInventoryOnNoPerm();
        this.isIntractable = gui.isIntractable;
        this.itemPermissions = gui.getItemPermissions();
        this.name = gui.getName();
        this.noPermError = gui.getNoPermError();
        this.soundOnNoPerm = gui.isSoundOnNoPerm();
        this.takeItemOnNoPerm = gui.isTakeItemOnNoPerm();
        this.useCustomListener = gui.isUseCustomListener();
        this.rows = gui.getRows();
        this.type = gui.getType();
        this.inventory = gui.toInventory();
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(@NotNull Inventory inventory, @NotNull JavaPlugin plugin) {
        this.inventory = inventory;
        this.type = inventory.getType();
        this.items = inventory.getContents();
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, int rows, ItemStack[] items, HashMap<ItemStack, HashMap<ClickType, RockRunnable>> clickFunctions, HashMap<ItemStack, String> itemPermissions, @NotNull JavaPlugin plugin) {
        this.items = items;
        this.clickFunctions = clickFunctions;
        this.itemPermissions = itemPermissions;
        this.rows = rows;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, InventoryType type, ItemStack[] items, HashMap<ItemStack, HashMap<ClickType, RockRunnable>> clickFunctions, HashMap<ItemStack, String> itemPermissions, @NotNull JavaPlugin plugin) {
        this.items = items;
        this.clickFunctions = clickFunctions;
        this.itemPermissions = itemPermissions;
        this.type = type;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, int rows, ItemStack[] items, @NotNull JavaPlugin plugin) {
        this.items = items;
        this.rows = rows;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, InventoryType type, ItemStack[] items, @NotNull JavaPlugin plugin) {
        this.items = items;
        this.type = type;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, ItemStack[] items, @NotNull JavaPlugin plugin) {
        this.items = items;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, int rows, @NotNull JavaPlugin plugin) {
        items = new ItemStack[rows * 9];
        this.rows = rows;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, @NotNull InventoryType type, @NotNull JavaPlugin plugin) {
        items = new ItemStack[type.getDefaultSize()];
        this.type = type;
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Gui(String guiName, @NotNull JavaPlugin plugin) {
        items = new ItemStack[rows * 9];
        this.name = guiName;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Helper Methods
     */
    public Gui setItem(ItemStack item, int slot, String permission, RockRunnable action, ClickType @NotNull ... clickTypes) {
        items[slot] = item;
        HashMap<ClickType, RockRunnable> clickTypeMap = new HashMap<>();
        for (ClickType clickType : clickTypes) clickTypeMap.put(clickType, action);
        clickFunctions.put(item, clickTypeMap);
        itemPermissions.put(item, permission);
        return this;
    }
    public Gui setItem(ItemStack item, int slot, RockRunnable action, ClickType @NotNull ... clickTypes) {
        items[slot] = item;
        HashMap<ClickType, RockRunnable> clickTypeMap = new HashMap<>();
        for (ClickType clickType : clickTypes) clickTypeMap.put(clickType, action);
        clickFunctions.put(item, clickTypeMap);
        return this;
    }
    public Gui setItem(ItemStack item, int slot, String permission, HashMap<ClickType, RockRunnable> clickTypeMap) {
        items[slot] = item;
        clickFunctions.put(item, clickTypeMap);
        itemPermissions.put(item, permission);
        return this;
    }
    public Gui setItem(ItemStack item, int slot, HashMap<ClickType, RockRunnable> clickTypeMap) {
        items[slot] = item;
        clickFunctions.put(item, clickTypeMap);
        return this;
    }
    public Gui setItem(ItemStack item, int slot) {
        items[slot] = item;
        return this;
    }

    public ItemModifier modifyItem(ItemStack item) {
        return new ItemModifier(item, this);
    }

    public Gui setItems(ItemStack[] items, HashMap<ItemStack, HashMap<ClickType, RockRunnable>> clickFunctions, HashMap<ItemStack, String> itemPermissions) {
        this.items = items;
        this.itemPermissions = itemPermissions;
        this.clickFunctions = clickFunctions;
        return this;
    }
    public Gui setItems(ItemStack[] items, HashMap<ItemStack, HashMap<ClickType, RockRunnable>> clickFunctions) {
        this.items = items;
        this.clickFunctions = clickFunctions;
        return this;
    }
    public Gui setItems(ItemStack[] items) {
        this.items = items;
        return this;
    }

    public Gui setCloseInventoryOnNoPerm(boolean closeInventoryOnNoPerm) {
        this.closeInventoryOnNoPerm = closeInventoryOnNoPerm;
        return this;
    }

    public Gui setIntractable(boolean intractable) {
        isIntractable = intractable;
        return this;
    }

    public Gui setSoundOnNoPerm(boolean soundOnNoPerm) {
        this.soundOnNoPerm = soundOnNoPerm;
        return this;
    }

    public Gui setTakeItemOnNoPerm(boolean takeItemOnNoPerm) {
        this.takeItemOnNoPerm = takeItemOnNoPerm;
        return this;
    }

    public Gui setUseCustomListener(boolean useCustomListener) {
        this.useCustomListener = useCustomListener;
        return this;
    }

    public Gui setName(String name) {
        this.name = name;
        return this;
    }

    public Gui setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public Gui setNoPermError(String noPermError) {
        this.noPermError = noPermError;
        return this;
    }

    public Gui setFiller(ItemStack filler) {
        this.filler = filler;
        return this;
    }

    public Gui setOutline(ItemStack outline) {
        this.outLineItem = outline;
        return this;
    }

    public Gui setType(InventoryType type) {
        this.type = type;
        return this;
    }

    public Gui setPlugin(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        return this;
    }

    // Getters.
    public ItemStack getItem(int itemSlot) {
        return items[itemSlot];
    }

    public Inventory toInventory() {
        updateFillerItem();
        inventory.setContents(items);
        return inventory;
    }

    // ------------------------------------- //
    // Deprecated methods.
    //
    // These methods are for very advanced usage, and you should not have to use them!

    /**
     * THIS IS DEPRECATED!
     * Use the new .refresh() method
     */
    @Deprecated
    public void refreshGuiContents() {
        this.inventory.setContents(this.items);
    }

    // @Deprecated
    public HashMap<ItemStack, HashMap<ClickType, RockRunnable>> getClickFunctions() {
        return clickFunctions;
    }

    // @Deprecated
    public void setClickFunctions(HashMap<ItemStack, HashMap<ClickType, RockRunnable>> clickFunctions) {
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

    private void updateFillerItem() {
        for (int i = 0; i < items.length; i++) {
            if ((items[i] == null || items[i].getType().equals(Material.AIR)) && filler != null) items[i] = filler;
        }
    }

    private void updateOutLine() {
        if (outLineItem == null) return;
        int offset = (rows-1)*9;
        for (int i = 0; i < 9; i++) {
            if (items[i] == null || items[i].getType().equals(Material.AIR)) items[i] = outLineItem;
            if ((items[i+offset] == null || items[i+offset].getType().equals(Material.AIR)) && rows > 1) items[i+offset] = outLineItem;
        }
        if (rows > 2) {
            for (int i = 0; i < rows-2; i++) {
                int sOffset = (1+i)*9;
                if (items[sOffset + 1] == null || items[sOffset + 1].getType().equals(Material.AIR)) items[sOffset] = outLineItem;
                if (items[sOffset + 8] == null || items[sOffset + 8].getType().equals(Material.AIR)) items[sOffset + 8] = outLineItem;
            }
        }
    }

    public Gui refresh(@NotNull Player player) {
        if (player.getOpenInventory().getTitle().equals(CC.translate(name))) {
            updateFillerItem();
            player.getOpenInventory().getTopInventory().setContents(items);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gui)) return false;
        Gui guiTest = (Gui) o;
        return getRows() == guiTest.getRows() && takeItemOnNoPerm == guiTest.takeItemOnNoPerm && isCloseInventoryOnNoPerm() == guiTest.isCloseInventoryOnNoPerm() && isIntractable() == guiTest.isIntractable() && isSoundOnNoPerm() == guiTest.isSoundOnNoPerm() && inventory.equals(guiTest.inventory) && name.equals(guiTest.name) && Arrays.equals(getItems(), guiTest.getItems()) && getClickFunctions().equals(guiTest.getClickFunctions()) && getItemPermissions().equals(guiTest.getItemPermissions()) && Objects.equals(getNoPermError(), guiTest.getNoPermError());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(inventory, getName(), getRows(), getClickFunctions(), getItemPermissions(), isTakeItemOnNoPerm(), isIntractable(), isUseCustomListener(), isCloseInventoryOnNoPerm(), isSoundOnNoPerm(), getNoPermError());
        result = 31 * result + Arrays.hashCode(getItems());
        return result;
    }

    public void openGui(Player player) {
        if (type.equals(InventoryType.CHEST))
            inventory = Bukkit.createInventory(player, this.rows * 9, ChatColor.translateAlternateColorCodes('&', this.name));
        else
            inventory = Bukkit.createInventory(player, type, ChatColor.translateAlternateColorCodes('&', this.name));
        if (type.equals(InventoryType.CHEST)) {
            updateFillerItem();
            updateOutLine();
        }
        inventory.setContents(items);
        player.openInventory(inventory);
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
        if (itemClicked == null || itemClicked.equals(filler) || itemClicked.equals(outLineItem)) return;
        if ((!itemPermissions.containsKey(itemClicked) || whoClicked.hasPermission(itemPermissions.get(itemClicked))) && clickFunctions.containsKey(itemClicked)) {
            if (!clickFunctions.get(itemClicked).containsKey(clickType)) return;
            clickFunctions.get(itemClicked).get(clickType).run(whoClicked);
        } else {
            ev.setCancelled(!takeItemOnNoPerm);
            if (closeInventoryOnNoPerm) whoClicked.closeInventory();
            if (soundOnNoPerm) whoClicked.playSound(whoClicked.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            if (!noPermError.isEmpty()) whoClicked.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermError));
        }
        ev.setCancelled(!isIntractable);
    }
}
