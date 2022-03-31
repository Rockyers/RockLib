package me.rockyers.rocklib.abstracts;

import org.bukkit.entity.Player;
import java.util.List;

/**
 * SubCommand is an abstract made to be used with a CommandManager
 * @author Rockyers
 * @since 1.6.1
 * @see me.rockyers.rocklib.objects.CommandManager
 */
public abstract class SubCommand {
    /**
     * Name of the subcommand, the actual input in chat
      */
    public abstract String getName();

    /**
     * A short description of what the SubCommand does
      */
    public abstract String getDescription();

    /**
     * The full command, including the parent command (I plan on removing this soon)
     */
    public abstract String getSyntax();

    /**
     * The permission required to run the SubCommand, set to "" or null for no permission
     */
    public abstract String getPermission();

    /**
     * The message sent to the player if they try to run the SubCommand without the required permission
     */
    public abstract String getNoPermMessage();

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
