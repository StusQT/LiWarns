package me.project.li_warns.commands.unwarn;

import me.project.li_warns.Main;
import me.project.li_warns.warn_config.LogAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnWarnCommand implements CommandExecutor {
    private final Main plugin;

    public UnWarnCommand(@NotNull Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found");
            return true;
        }

        if(commandSender.equals(target)) {
            commandSender.sendMessage("§cYou can't remove the warning from yourself");
            return true;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length - 1; i++)
            sb.append(args[i]).append(" ");
        sb.append(args[args.length - 1]);
        String cause = sb.toString();

        int warnCount = plugin.getWarningBridge().removeLastWarn(target);

        if (warnCount == -1) commandSender.sendMessage(ChatColor.GOLD + "Player " + args[0] + " has no warnings");
        else {
            target.sendMessage(ChatColor.GREEN + commandSender.getName() + " removed the last warning from you");
            commandSender.sendMessage(ChatColor.GREEN + "Success!");
        }

        plugin.getWarnsLogger().log(LogAction.UNWARN,
                "Administrator " + commandSender.getName() + " removed last warning from player "
                        + target.getName() + ". Cause: " + cause);

        return true;
    }
}
