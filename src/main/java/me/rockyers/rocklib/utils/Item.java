package me.rockyers.rocklib.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Item {

    Material material;
    String name;
    ArrayList<String> lore = new ArrayList<String>();

    ItemStack item;

    public Item(String name, Material material, ArrayList<String> lore) {
        this.material = material;
        this.name = name;
        this.lore = lore;
    }

    public Item(String name, Material material, String lore) {
        this.material = material;
        this.name = name;
        this.lore.add(lore);
    }

    public void AddLore(String nextLine) {
        lore.add(nextLine);
    }

    public void ChangeMaterial(Material material) {
        this.material = material;
    }

    public void ChangeName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
        itemMeta.setLore(this.lore);
        item.setItemMeta(itemMeta);

        return item;
    }

}
