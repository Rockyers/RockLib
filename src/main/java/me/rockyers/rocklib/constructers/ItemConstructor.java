package me.rockyers.rocklib.constructers;

import me.rockyers.rocklib.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

/**
 * ItemConstructor is an Object used for simple ItemStack creation.
 * Intended to be used by Method Chaining.
 *
 * @author Rockyers
 * @since 1.6.1
 * @see ItemStack
 */
public class ItemConstructor {
    /**
     * The final usable ItemStack
     */
    private ItemStack finalItem;

    /**
     * Variables for the ItemConstructor. Names should be self-explanatory.
     */
    private final Material material;
    private String name;
    private ArrayList<String> lore = new ArrayList<>();
    private Player skullSkinOwner;
    private Map<Enchantment, Integer> enchantments;
    private boolean unbreakable = false;

    /**
     * Constructor for the ItemConstructor.
     * @param material The material for the Item being created
     */
    public ItemConstructor(Material material) {
        this.material = material;
    }

    /**
     * Constructor for the ItemConstructor, for copying from ItemStacks
     * @param itemStack The ItemStack that you want to copy
     */
    public ItemConstructor(@NotNull ItemStack itemStack) {
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

    /**
     * Method to set the ItemStacks name
     * @param name The name for the ItemStack
     */
    public ItemConstructor setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Method to set the lore of the ItemStack to a single string
     * @param lore The Lore (String) for the ItemStack
     */
    public ItemConstructor setLore(String lore) {
        this.lore.clear();
        this.lore.add(lore);
        return this;
    }

    /**
     * Method to set the lore of the ItemStack to an ArrayList
     * @param lore The Lore (ArrayList) for the ItemStack
     */
    public ItemConstructor setLore(ArrayList<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Method to add one line of lore to the ItemStack
     * @param lore The lore you want to add
     */
    public ItemConstructor addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    /**
     * Method to set the PlayerHeadSkin of the ItemStack (Only if this.material is Material.PLAYER_HEAD)
     * @param uuid The UUID of the player you want to head skin to be
     */
    public ItemConstructor setHeadSkin(UUID uuid) {
        skullSkinOwner = Bukkit.getPlayer(uuid);
        return this;
    }

    /**
     * Method to add an enchantment to the ItemStack
     * @param enchantment The enchantment you want to add
     * @param powerLevel The power level of the enchantment
     */
    public ItemConstructor addEnchantment(Enchantment enchantment, Integer powerLevel) {
        enchantments.put(enchantment, powerLevel);
        return this;
    }

    /**
     * Method to remove an enchantment from the ItemStack
     * @param enchantment The enchantment you want to remove
     */
    public ItemConstructor removeEnchantment(Enchantment enchantment) {
        enchantments.remove(enchantment);
        return this;
    }

    /**
     * Method to set if the ItemStack is unbreakable
     * @param isUnbreakable If the ItemStack is unbreakable or not
     */
    public ItemConstructor setUnbreakable(boolean isUnbreakable) {
        unbreakable = isUnbreakable;
        return this;
    }

    /**
     * The method to create the final ItemStack
     * @return The final usable ItemStack
     */
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
