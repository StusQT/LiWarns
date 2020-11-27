package me.project.li_warns.commands.warnlist;

import me.project.li_warns.Main;
import me.project.li_warns.gui.fake_book.FakeBook;
import me.project.li_warns.warn_config.LiWarnPlayer;
import me.project.li_warns.warn_config.PlayerWarning;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WarnListCommand implements CommandExecutor {
    private final Main plugin;

    public WarnListCommand(@NotNull Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You are not a player. You haven't any warnings!");
            return true;
        }

        Player target = (Player) commandSender;
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta == null) {
            target.sendMessage("§cAn unknown error has occurred. Please inform the server administrator about it");
            return true;
        }

        meta.setAuthor("LiWarns plugin");
        meta.setTitle("Your warnings");

        LiWarnPlayer warnPlayer = plugin.getWarningBridge().getByUUID(target.getUniqueId());
        List<String> pages = new ArrayList<>();

        if (warnPlayer == null) {
            pages.add("§2Congratulations!\n\nYou haven't any warnings!\n\nThank You");
        } else {
            List<PlayerWarning> warnings = warnPlayer.getWarnings();
            pages.add("§2§nWou have " + warnPlayer.getWarnCount() + " warnings\n\n§6They are on the following pages");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy\n   HH:mm:ss", Locale.US);
            for (int i = 0; i < warnings.size(); ++i) {
                PlayerWarning warn = warnings.get(i);
                String page = "§6Warning §8#" + (i + 1)
                        + "\n\n§1§nIssued by§8§o " + warn.getAdminName()
                        + "\n\n§2§nCause§8§o " + warn.getCause()
                        + "\n\n§5§nAt§8§o " + warn.getTime().format(formatter);

                pages.add(page);
            }
        }
        meta.setPages(pages);
        book.setItemMeta(meta);

        if(!FakeBook.openBook((Player) commandSender, book)) {
            for (String text : pages)
                target.sendMessage(text);
        }

        return true;
    }
}
