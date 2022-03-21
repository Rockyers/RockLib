package me.rockyers.rocklib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class CC {

    @Contract("_ -> new")
    public @NotNull String translate(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static @NotNull ArrayList<String> translate(@NotNull List<String> message) {
        ArrayList<String> list = new ArrayList<>();
        for (String string : message) {
            list.add(translate(string));
        }
        return list;
    }
}