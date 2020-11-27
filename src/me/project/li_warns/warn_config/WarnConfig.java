package me.project.li_warns.warn_config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarnConfig {
    protected File configFile;
    protected FileConfiguration config;

    public WarnConfig(File file) {
        this.configFile = file;
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Nullable
    public LiWarnPlayer getByUUID(@NotNull UUID uuid) {
        String path = "players." + uuid;
        if (!config.contains(path)) return null;
        List<?> list = config.getList(path + ".warnings");

        return list == null ? null : new LiWarnPlayer(uuid, toPWList(list));
    }

    public void setByUUID(@NotNull UUID uuid, @NotNull LiWarnPlayer player) {
        String path = "players." + uuid;
        config.set(path + ".warnCount", player.getWarnCount());
        config.set(path + ".warnings", player.getWarnings());

        saveConfig();
    }

    public void clearSection(@NotNull UUID uuid) {
        config.set("players." + uuid, null);
        saveConfig();
    }

    public void clearSection(@NotNull String path) {
        config.set(path, null);
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<PlayerWarning> toPWList(List<?> list) {
        List<PlayerWarning> playerWarnings = new ArrayList<>();
        for (Object pw : list)
            if (pw instanceof PlayerWarning) playerWarnings.add((PlayerWarning) pw);

        return playerWarnings;
    }
}
