package me.rockyers.rocklib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class CC {

    public String translate(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static ArrayList<String> translate(List<String> message) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : message) {
            list.add(translate(string));
        }
        return list;
    }
}