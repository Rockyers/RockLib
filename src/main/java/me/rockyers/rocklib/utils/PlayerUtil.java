package me.rockyers.rocklib.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class PlayerUtil {
    public void send(Player p, String msg) {
        p.sendMessage(CC.translate(msg));
    }

    public void send(Player p, String[] msg) {
        for (String s : msg) {
            p.sendMessage(CC.translate(s));
        }
    }

    public void send(Player p, TextComponent textComponent) {
        p.spigot().sendMessage(textComponent);
    }

    public void sendAll(String msg) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(CC.translate(msg));
        }
    }

    public void sendAll(String[] msg) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            for (String s : msg) {
                all.sendMessage(CC.translate(s));
            }
        }
    }

    public void sendAll(TextComponent textComponent) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.spigot().sendMessage(textComponent);
        }
    }

    public List<Entity> getEntitiesWithinRadius(final Location location, final double radius) {

        final double expander = 16.0D;

        final double x = location.getX();
        final double z = location.getZ();

        final int minX = (int) Math.floor((x - radius) / expander);
        final int maxX = (int) Math.floor((x + radius) / expander);

        final int minZ = (int) Math.floor((z - radius) / expander);
        final int maxZ = (int) Math.floor((z + radius) / expander);

        final World world = location.getWorld();

        List<Entity> entities = new LinkedList<>();

        for (int xVal = minX; xVal <= maxX; xVal++) {

            for (int zVal = minZ; zVal <= maxZ; zVal++) {

                if (!world.isChunkLoaded(xVal, zVal)) continue;

                for (Entity entity : world.getChunkAt(xVal, zVal).getEntities()) {
                    //We have to do this due to stupidness
                    if (entity == null) continue;

                    //Make sure the entity is within the radius specified
                    if (entity.getLocation().distanceSquared(location) > radius * radius) continue;

                    entities.add(entity);
                }
            }
        }

        return entities;
    }
}
