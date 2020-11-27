package me.project.li_warns.commands.warn;

import me.project.li_warns.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarnCommand implements CommandExecutor {
    private final Main plugin;
    private final int maxWarnings;

    public WarnCommand(@NotNull Main plugin, int maxWarnings) {
        this.plugin = plugin;
        this.maxWarnings = maxWarnings;
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
            commandSender.sendMessage("§cYou can't warn yourself");
            return true;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length - 1; i++)
            sb.append(args[i]).append(" ");
        sb.append(args[args.length - 1]);
        String reason = sb.toString();

        int warnCount = plugin.getWarningBridge().addWarn(target, commandSender.getName(), reason);
        if (warnCount > this.maxWarnings) {
            target.kickPlayer(plugin.getKickMessage());
            plugin.getWarningBridge().clearSection(target.getUniqueId());
        } else {
            target.sendMessage(ChatColor.GOLD + "You received a warning from " + commandSender.getName());
            commandSender.sendMessage(ChatColor.GREEN + "Success!");
        }

        return true;
    }
}
