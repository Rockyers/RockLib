package me.rockyers.rocklib.abstracts;

import org.bukkit.entity.Player;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * SubCommand is an abstract made to be used with a CommandManager
 * @author Rockyers
 * @since 1.6.1
 * @see me.rockyers.rocklib.objects.CommandManager
 */
public abstract class SubCommand {
    /**
     * TODO: DOCUMENT
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface SubCommandInfo {
        String name();
        String description();
        String syntax();
        String permission() default "";
        String permessionmessage() default "";
    }

    SubCommandInfo info = this.getClass().getAnnotation(SubCommandInfo.class);

    /**
     * Name of the subcommand, the actual input in chat
     */
    public String getName() {
        return info.name();
    }

    /**
     * A short description of what the SubCommand does
     */
    public String getDescription() {
        return info.description();
    }

    /**
     * The full command, including the parent command (I plan on removing this soon)
     */
    public String getSyntax() {
        return info.syntax();
    }

    /**
     * The permission required to run the SubCommand, set to "" or null for no permission
     */
    public String getPermission() {
        return info.permission();
    }

    /**
     * The message sent to the player if they try to run the SubCommand without the required permission
     */
    public String getNoPermMessage() {
        return info.permessionmessage();
    }

    /**
     * The actual code for the SubCommand
     * @param player The player that ran the SubCommand
     * @param args The arguments supplied by the player (Including this SubCommand)
     */
    public abstract void perform(Player player, String[] args);

    /**
     * The TabCompleter for the SubCommand
     * @param player The player typing the SubCommand
     * @param args The arguments supplied by the player (Including this SubCommand)
     */
    public abstract List<String> getSubcommandArguments(Player player, String[] args);
}
