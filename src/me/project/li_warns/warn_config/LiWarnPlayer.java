package me.project.li_warns.warn_config;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class LiWarnPlayer {
    protected UUID uuid;
    protected int warnCount;
    protected List<PlayerWarning> warnings;

    public LiWarnPlayer(@NotNull UUID uuid, @NotNull List<PlayerWarning> warnings) {
        this.uuid = uuid;
        this.warnings = warnings;
        warnCount = warnings.size();
    }

    public void setWarnCount(int newCount) {
        warnCount = newCount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getWarnCount() {
        return warnCount;
    }

    public List<PlayerWarning> getWarnings() {
        return warnings;
    }
}
