package me.rockyers.rocklib.objects;

import org.bukkit.entity.Player;

/**
 * RockRunnable is an Interface created specifically for RockLib. Basically just a Runnable but with a Player parameter. Used for Gui, Item, and CommandManager
 *
 * @author Rockyers
 * @since 1.6.1
 * @see Runnable
 * @see Gui
 * @see Item
 * @see CommandManager
 */
@FunctionalInterface
public interface RockRunnable {
    void run(Player thePlayer);
}
