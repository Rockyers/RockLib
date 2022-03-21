package me.rockyers.rocklib.utils;

import lombok.Getter;
import lombok.Setter;
import me.rockyers.rocklib.RockLib;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Item implements Listener {

    @Getter private Material material;
    @Getter private String name;
    @Getter private ArrayList<String> lore = new ArrayList<>();
    @Getter private Player skullSkinOwner;
    @Getter private Map<Enchantment, Integer> enchantments;
    @Getter private boolean unbreakable = false;

    @Getter HashMap<Action, Runnable> itemFunctions;
    @Getter private String permission, noPermErrorMsg;
    @Getter private boolean soundOnNoPerm = true;

    ItemStack itemStack;

    public Item(ItemStack itemStack) {
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
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
    }

    public Item(Material material) {
        this.material = material;
        itemStack = new ItemStack(this.material);
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
    }

    public Item(String name, Material material) {
        this.material = material;
        this.name = name;
        itemStack = new ItemStack(this.material);
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
    }

    public Item(Material material, String name, ArrayList<String> lore) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        itemStack = new ItemStack(this.material);
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
    }

    public Item(Material material, String name, String lore) {
        this.material = material;
        this.name = name;
        this.lore.add(lore);
        itemStack = new ItemStack(this.material);
        RockLib.getInstance().getServer().getPluginManager().registerEvents(this, RockLib.getInstance());
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

    public Item setFunction(Action action, Runnable runnable) {
        itemFunctions.put(action, runnable);
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
        if (itemFunctions.containsKey(action)) {
            if (permission.isEmpty() || player.hasPermission(permission)) {
                itemFunctions.get(action).run();
            } else {
                if (!noPermErrorMsg.isEmpty()) player.sendMessage(CC.translate(noPermErrorMsg));
            }
        }
    }
}
