package me.rockyers.rocklib.abstracts;

import org.bukkit.entity.Player;
import java.util.List;

public abstract class SubCommand {
    // name of the subcommand ex. /command <subcommand> <-- that
    public abstract String getName();

    // ex. "This command teleports beans"
    public abstract String getDescription();

    // How to use command ex. /command subcommand <player>
    public abstract String getSyntax();

    // The permission to run the command (make it just "" or null for no permission)
    public abstract String getPermission();

    // The message send it the player tries to run the subcommand without the permission
    public abstract String getNoPermMessage();

    // code for the subcommand
    public abstract void perform(Player player, String[] args);

    // The tab executor for the subcommand
    public abstract List<String> getSubcommandArguments(Player player, String[] args);
}
