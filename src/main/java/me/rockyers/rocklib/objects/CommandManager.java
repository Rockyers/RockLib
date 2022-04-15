package me.rockyers.rocklib.objects;

import lombok.Getter;
import me.rockyers.rocklib.abstracts.SubCommand;
import me.rockyers.rocklib.utils.CC;
import me.rockyers.rocklib.utils.PlayerUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CommandManager is an object used to make command handling much easier (specifically with SubCommands)
 * @author Rockyers
 * @since 1.6.1
 * @see me.rockyers.rocklib.abstracts.SubCommand
 */
public class CommandManager implements TabExecutor {
    /**
     * The variables of the CommandManager, names should be self-explanatory
     */
    @Getter private ArrayList<SubCommand> subCommands = new ArrayList<>();
    @Getter private String permission;
    @Getter private String noPermMessage = CC.translate("&cYou do not have permission to run that command!");
    @Getter private RockRunnable whenNoArgs = thePlayer -> {
        thePlayer.sendMessage(CC.translate("&f------Help------"));
        for (SubCommand subCommand : getSubCommands()) {
            HoverMessage hoverMessage = new HoverMessage(CC.translate("&b&l" + subCommand.getSyntax() + " &r&7- &f" + subCommand.getDescription()), "&eClick to run", ClickEvent.Action.SUGGEST_COMMAND, subCommand.getSyntax());
            PlayerUtil.send(thePlayer, hoverMessage.toTextComponent());
        }
        thePlayer.sendMessage(CC.translate("&f----------------"));
    };

    @Getter private boolean playSoundOnTab = false;

    /**
     * Full constructor
     * @param commandName The name of the command, what the player types to run it
     * @param plugin The JavaPlugin of your plugin, your main class
     * @param whenNoArgs What runs when they don't supply any arguments or SubCommands (RockRunnable}
     * @param permission The permission required to run the command
     * @see me.rockyers.rocklib.objects.RockRunnable
     */
    public CommandManager(String commandName, @NotNull JavaPlugin plugin, RockRunnable whenNoArgs, String permission){
        this.whenNoArgs = whenNoArgs;
        this.permission = permission;
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
    }

    /**
     * Normal constructor
     * @param commandName The name of the command, what the player types to run it
     * @param plugin The JavaPlugin of your plugin, your main class
     * @param whenNoArgs What runs when they don't supply any arguments or SubCommands (RockRunnable}
     * @see me.rockyers.rocklib.objects.RockRunnable
     */
    public CommandManager(String commandName, @NotNull JavaPlugin plugin, RockRunnable whenNoArgs){
        this.whenNoArgs = whenNoArgs;
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
    }

    /**
     * Basic constructor
     * @param commandName The name of the command, what the player types to run it
     * @param plugin The JavaPlugin of your plugin, your main class
     */
    public CommandManager(String commandName, @NotNull JavaPlugin plugin){
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
    }

    /**
     * Method to set the permission required to use the command
     * @param permission the permission
     */
    public CommandManager setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Method to set the message sent to players when they try to run the command without the permission to do so
     * @param message The message
     */
    public CommandManager setNoPermMessage(String message) {
        this.noPermMessage = message;
        return this;
    }

    /**
     * Method to set what happens when no arguments or SubCommands are specified (RockRunnable)
     * @param whenNoArgs What happens (RockRunnable)
     * @see me.rockyers.rocklib.objects.RockRunnable
     */
    public CommandManager setWhenNoArgs(RockRunnable whenNoArgs) {
        this.whenNoArgs = whenNoArgs;
        return this;
    }

    /**
     * Method to set the SubCommands
     * @param subCommands The list of SubCommands
     */
    public CommandManager setSubCommands(ArrayList<SubCommand> subCommands) {
        this.subCommands = subCommands;
        return this;
    }

    /**
     * Method to add SubCommands
     * @param subCommands The SubCommands to add
     */
    public CommandManager addSubCommand(SubCommand @NotNull ... subCommands) {
        for (SubCommand subCommand : subCommands) getSubCommands().add(subCommand);
        return this;
    }

    /**
     * Method to remove a SubCommand
     * @param subCommand The SubCommand to remove
     */
    public CommandManager removeSubCommand(SubCommand subCommand) {
        getSubCommands().remove(subCommand);
        return this;
    }

    /**
     * Method to check if an object equals this
     * @param o The object to compare
     * @return If the objects are equal (boolean)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandManager)) return false;
        CommandManager that = (CommandManager) o;
        return Objects.equals(getSubCommands(), that.getSubCommands()) &&
               Objects.equals(getPermission(), that.getPermission()) &&
               Objects.equals(getNoPermMessage(), that.getNoPermMessage()) &&
               getWhenNoArgs().equals(that.getWhenNoArgs());
    }

    /**
     * Method to get this objects HashCode
     * @return This objects HashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSubCommands(), getPermission(), getNoPermMessage(), getWhenNoArgs());
    }

    /**
     * Method to get this object as a String
     * @return A String built out of this Object
     */
    @Override
    public String toString() {
        return "CommandManager{" +
                "subCommands=" + subCommands +
                ", permission='" + permission + '\'' +
                ", noPermMessage='" + noPermMessage + '\'' +
                ", whenNoArgs=" + whenNoArgs +
                '}';
    }

    /**
     * What happens when the Command is run
     * @param sender Who ran the command
     * @param command The command ran
     * @param label The label of the command ran
     * @param args The arguments supplied
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (permission == null || permission.isEmpty() || p.hasPermission(permission)) {
                if (args.length > 0) {
                    for (SubCommand subCommand : getSubCommands()) {
                        if (args[0].equalsIgnoreCase(subCommand.getName())) {
                            if (subCommand.getPermission() == null || subCommand.getPermission().isEmpty() || p.hasPermission(subCommand.getPermission())) {
                                subCommand.perform(p, args);
                            } else if (subCommand.getPermission() != null && !subCommand.getPermission().isEmpty() && !p.hasPermission(subCommand.getPermission())){
                                if (subCommand.getNoPermMessage() != null && !subCommand.getNoPermMessage().isEmpty()) PlayerUtil.send(p, subCommand.getNoPermMessage());
                            }
                        }
                    }
                } else {
                    whenNoArgs.run(p);
                }
            } else if (permission != null && !permission.isEmpty() && !p.hasPermission(permission)){
                if (noPermMessage != null && !noPermMessage.isEmpty()) PlayerUtil.send(p, noPermMessage);
            }
        }

        return true;
    }

    /**
     * The TabCompletion
     * @param sender Who is typing the command
     * @param command The command being typed
     * @param alias The alias of that command
     * @param args The arguments supplied
     * @return The list of Strings for the TabCompleter
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (playSoundOnTab) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            if (permission == null || permission.isEmpty() || player.hasPermission(permission)) {
                if (args.length == 1) {
                    ArrayList<String> subcommandsArguments = new ArrayList<>();

                    for (SubCommand subCommand : getSubCommands()) {
                        subcommandsArguments.add(subCommand.getName());
                    }

                    return subcommandsArguments;
                } else if (args.length >= 2) {
                    for (SubCommand subCommand : getSubCommands()) {
                        if (args[0].equalsIgnoreCase(subCommand.getName())) {
                            return subCommand.getSubcommandArguments(player, args);
                        }
                    }
                }
            }
        }
        return null;
    }
}
