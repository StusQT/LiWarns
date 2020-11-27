package me.project.li_warns;

import me.project.li_warns.commands.unwarn.UnWarnCommand;
import me.project.li_warns.commands.warn.WarnCommand;
import me.project.li_warns.commands.warnlist.WarnListCommand;
import me.project.li_warns.plugin_logger.PluginLogger;
import me.project.li_warns.warn_config.PlayerWarning;
import me.project.li_warns.warn_config.WarnToConfigBridge;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends JavaPlugin {
    static {
        ConfigurationSerialization.registerClass(PlayerWarning.class, "PlayerWarning");
    }

    private final WarnToConfigBridge warningBridge;
    private final int maxWarnings;
    private final String kickMessage;
    private PluginLogger warnsLogger;

    public Main() {
        File defaultConfigFile = new File(super.getDataFolder() + File.separator + "config.yml");
        if (!defaultConfigFile.exists()) {
            saveDefaultConfig();
            maxWarnings = 3;
            kickMessage = "Excuse me, you got too many warnings and got kicked out";
        } else {
            FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigFile);
            maxWarnings = defaultConfig.getInt("maxWarnings");
            kickMessage = defaultConfig.getString("kickMessage");
        }

        warningBridge = new WarnToConfigBridge(new File(this.getDataFolder() + File.separator + "warns.yml"), maxWarnings);

        try {
            warnsLogger = new PluginLogger(new File(super.getDataFolder() + File.separator + "warns.log"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(super.getCommand("warn")).setExecutor(new WarnCommand(this, maxWarnings));
        Objects.requireNonNull(super.getCommand("unwarn")).setExecutor(new UnWarnCommand(this));
        Objects.requireNonNull(super.getCommand("warnlist")).setExecutor(new WarnListCommand(this));
    }

    @Override
    public void onDisable() {
        try {
            warnsLogger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public @NotNull WarnToConfigBridge getWarningBridge() {
        return warningBridge;
    }

    public int getMaxWarnings() {
        return maxWarnings;
    }

    public @NotNull String getKickMessage() {
        return kickMessage;
    }

    public PluginLogger getWarnsLogger() {
        return warnsLogger;
    }
}
