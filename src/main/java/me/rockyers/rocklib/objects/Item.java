package me.rockyers.rocklib.objects;

import lombok.Getter;
import me.rockyers.rocklib.RockLib;
import me.rockyers.rocklib.utils.CC;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Item implements Listener {

    @Getter private Material material;
    @Getter private String name;
    @Getter private ArrayList<String> lore = new ArrayList<>();
    @Getter private Player skullSkinOwner;
    @Getter private Map<Enchantment, Integer> enchantments = new HashMap<>();
    @Getter private boolean unbreakable = false;

    @Getter HashMap<Action, Runnable> itemFunctions = new HashMap<>();
    @Getter private String permission, noPermErrorMsg;
    @Getter private boolean soundOnNoPerm = true;

    @Getter private JavaPlugin plugin;

    ItemStack itemStack;

    public Item(@NotNull ItemStack itemStack, JavaPlugin plugin) {
        this.itemStack = itemStack;
        this.material = itemStack.getType();
        this.name = Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
        this.lore = (ArrayList<String>) itemStack.getItemMeta().getLore();
        if (itemStack.getType().equals(Material.PLAYER_HEAD)) {
            SkullMeta sMeta = (SkullMeta) itemStack.getItemMeta();
            skullSkinOwner = (Player) sMeta.getOwningPlayer();
        }
        this.enchantments = itemStack.getEnchantments();
        this.unbreakable = itemStack.getItemMeta().isUnbreakable();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Item(Material material, @NotNull JavaPlugin plugin) {
        this.material = material;
        itemStack = new ItemStack(this.material);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Item(String name, Material material, @NotNull JavaPlugin plugin) {
        this.material = material;
        this.name = name;
        itemStack = new ItemStack(this.material);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Item(Material material, String name, ArrayList<String> lore, @NotNull JavaPlugin plugin) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        itemStack = new ItemStack(this.material);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Item(Material material, String name, String lore, @NotNull JavaPlugin plugin) {
        this.material = material;
        this.name = name;
        this.lore.add(lore);
        itemStack = new ItemStack(this.material);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Item AddLore(String nextLine) {
        lore.add(nextLine);
        return this;
    }

    public Item setLore(String lore) {
        this.lore.clear();
        this.lore.add(lore);
        return this;
    }

    public Item setLore(ArrayList<String> lore) {
        this.lore = lore;
        return this;
    }

    public Item setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Item setFunction(Runnable runnable, Action action) {
        if (itemFunctions == null) itemFunctions = new HashMap<>();
        itemFunctions.put(action, runnable);
        return this;
    }

    public Item setFunction(Runnable runnable, Action... actions) {
        if (itemFunctions == null) itemFunctions = new HashMap<>();
        for (Action action : actions) itemFunctions.put(action, runnable);
        return this;
    }

    public Item setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Item setNoPermErrorMsg(String msg) {
        noPermErrorMsg = msg;
        return this;
    }

    public Item setPlugin(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        return this;
    }

    public Item setSoundOnNoPerm(boolean soundOnNoPerm) {
        this.soundOnNoPerm = soundOnNoPerm;
        return this;
    }

    public ItemStack toItemStack() {
        itemStack.setType(material);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        if (name != null) meta.setDisplayName(CC.translate(name));
        if (!lore.isEmpty()) meta.setLore(CC.translate(lore));
        meta.setUnbreakable(unbreakable);
        if (material.equals(Material.PLAYER_HEAD)) {
            SkullMeta sMeta = (SkullMeta) itemStack.getItemMeta();
            if (skullSkinOwner != null) sMeta.setOwningPlayer(skullSkinOwner);
        }
        itemStack.addEnchantments(enchantments);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent ev) {
        Action action = ev.getAction();
        Player player = ev.getPlayer();
        if (ev.getHand() == EquipmentSlot.OFF_HAND) return;
        if (itemFunctions.containsKey(action)) {
            if (permission == null || permission.isEmpty() || player.hasPermission(permission)) {
                itemFunctions.get(action).run();
            } else {
                if (permission != null && !permission.isEmpty() && !player.hasPermission(permission)) {
                    if (noPermErrorMsg != null && !noPermErrorMsg.isEmpty()) player.sendMessage(CC.translate(noPermErrorMsg));
                    if (soundOnNoPerm) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                }
            }
        }
    }
}
