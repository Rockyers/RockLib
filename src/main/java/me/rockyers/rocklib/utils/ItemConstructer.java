package me.rockyers.rocklib.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ItemConstructer {
    private ItemStack finalItem;

    private final Material material;
    private String name;
    private ArrayList<String> lore = new ArrayList<>();
    private Player skullSkinOwner;
    private Map<Enchantment, Integer> enchantments;
    private boolean unbreakable = false;

    public ItemConstructer(Material material) {
        this.material = material;
    }

    public ItemConstructer(@NotNull ItemStack itemStack) {
        this.finalItem = itemStack;
        this.material = itemStack.getType();
        this.name = Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
        this.lore = (ArrayList<String>) itemStack.getItemMeta().getLore();
        this.unbreakable = itemStack.getItemMeta().isUnbreakable();
        this.enchantments = itemStack.getEnchantments();
        if (itemStack.getType().equals(Material.PLAYER_HEAD)) {
            SkullMeta sMeta = (SkullMeta) itemStack.getItemMeta();
            if (sMeta.getOwningPlayer() != null) skullSkinOwner = (Player) sMeta.getOwningPlayer();
        }
    }

    public ItemConstructer setName(String name) {
        this.name = name;
        return this;
    }

    public ItemConstructer setLore(String lore) {
        this.lore.clear();
        this.lore.add(lore);
        return this;
    }

    public ItemConstructer setLore(ArrayList<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemConstructer addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemConstructer setHeadSkin(UUID uuid) {
        skullSkinOwner = Bukkit.getPlayer(uuid);
        return this;
    }

    public ItemConstructer addEnchantment(Enchantment enchantment, Integer powerLevel) {
        enchantments.put(enchantment, powerLevel);
        return this;
    }

    public ItemConstructer removeEnchantment(Enchantment enchantment) {
        enchantments.remove(enchantment);
        return this;
    }

    public ItemConstructer setUnbreakable(boolean isUnbreakable) {
        unbreakable = isUnbreakable;
        return this;
    }

    public ItemStack create() {
        finalItem = new ItemStack(material);
        ItemMeta meta = finalItem.getItemMeta();
        assert meta != null;
        if (name != null) meta.setDisplayName(CC.translate(name));
        if (lore != null) meta.setLore(CC.translate(lore));
        meta.setUnbreakable(unbreakable);
        if (finalItem.getType().equals(Material.PLAYER_HEAD)) {
            SkullMeta sMeta = (SkullMeta) meta;
            if (skullSkinOwner != null) sMeta.setOwningPlayer(skullSkinOwner);
        }
        if (enchantments != null && !enchantments.isEmpty()) finalItem.addEnchantments(enchantments);
        finalItem.setItemMeta(meta);
        return this.finalItem;
    }
}
