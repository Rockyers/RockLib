package me.rockyers.rocklib.objects;

import lombok.Getter;
import me.rockyers.rocklib.abstracts.SubCommand;
import me.rockyers.rocklib.utils.CC;
import me.rockyers.rocklib.utils.PlayerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager implements TabExecutor {

    // Interface variables
    @Getter private ArrayList<SubCommand> subCommands = new ArrayList<>();
    @Getter private String permission;
    @Getter private String noPermMessage = CC.translate("&cYou do not have permission to run that command!");
    @Getter private RockRunnable whenNoArgs = thePlayer -> {
        thePlayer.sendMessage(CC.translate("&f------Help------"));
        for (SubCommand subCommand : getSubCommands()) {
            HoverMessage hoverMessage = new HoverMessage(CC.translate("&b&l" + subCommand.getSyntax() + " &r&7- &f" + subCommand.getDescription()));
            PlayerUtil.send(thePlayer, hoverMessage.toTextComponent());
        }
        thePlayer.sendMessage(CC.translate("&f----------------"));
    };

    // Constructors
    public CommandManager(String commandName, @NotNull JavaPlugin plugin, RockRunnable whenNoArgs, String permission){
        this.whenNoArgs = whenNoArgs;
        this.permission = permission;
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
    }

    public CommandManager(String commandName, @NotNull JavaPlugin plugin, RockRunnable whenNoArgs){
        this.whenNoArgs = whenNoArgs;
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
    }

    public CommandManager(String commandName, @NotNull JavaPlugin plugin){
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(this);
    }

    // Setters
    public CommandManager setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandManager setNoPermMessage(String message) {
        this.noPermMessage = message;
        return this;
    }

    public CommandManager setWhenNoArgs(RockRunnable whenNoArgs) {
        this.whenNoArgs = whenNoArgs;
        return this;
    }

    public CommandManager setSubCommands(ArrayList<SubCommand> subCommands) {
        this.subCommands = subCommands;
        return this;
    }

    public CommandManager addSubCommand(SubCommand subCommand) {
        getSubCommands().add(subCommand);
        return this;
    }

    public CommandManager removeSubCommand(SubCommand subCommand) {
        getSubCommands().remove(subCommand);
        return this;
    }

    // Helper Methods
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

    @Override
    public int hashCode() {
        return Objects.hash(getSubCommands(), getPermission(), getNoPermMessage(), getWhenNoArgs());
    }

    @Override
    public String toString() {
        return "CommandManager{" +
                "subCommands=" + subCommands +
                ", permission='" + permission + '\'' +
                ", noPermMessage='" + noPermMessage + '\'' +
                ", whenNoArgs=" + whenNoArgs +
                '}';
    }

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

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (permission != null && !permission.isEmpty() && player.hasPermission(permission)) {
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
