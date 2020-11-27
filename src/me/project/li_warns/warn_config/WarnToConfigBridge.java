package me.project.li_warns.warn_config;


import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarnToConfigBridge extends WarnConfig {
    protected int maxWarns;

    public WarnToConfigBridge(@NotNull File configFile, int maxWarns) {
        super(configFile);
        this.maxWarns = maxWarns;
    }

    /**
     * Issues a warning to the player and writes information about it
     * to the configuration file
     *
     * @param target    the player who will be issued a warning
     * @param adminName the player name who was issued a warning
     * @param reason    reason for the warning
     * @return the number of warnings the player has so far
     **/
    public int addWarn(@NotNull Player target, @NotNull String adminName, @NotNull String reason) {
        UUID targetUUID = target.getUniqueId();
        LiWarnPlayer warnPlayer = super.getByUUID(targetUUID);
        if (warnPlayer == null) {
            List<PlayerWarning> warnings = new ArrayList<>();
            warnings.add(new PlayerWarning(adminName, reason));
            super.setByUUID(targetUUID, new LiWarnPlayer(targetUUID, warnings));

            return 1;
        }

        int countOfWarnings = warnPlayer.getWarnCount();
        if (++countOfWarnings > this.maxWarns)
            return countOfWarnings;

        warnPlayer.setWarnCount(countOfWarnings);
        warnPlayer.getWarnings().add(new PlayerWarning(adminName, reason));
        super.setByUUID(targetUUID, warnPlayer);

        return countOfWarnings;
    }

    /**
     * Removes the last warning from the specified player.
     * Clears the player section in the config file, if player hasn't warnings anymore.
     *
     * @param target the player from whom the last warning will be removed.
     * @return Count of player's warnings remaining.
     * If players hasn't warnings anymore - returns 0.
     * If the player had no warnings initially, returns -1.
     */
    public int removeLastWarn(@NotNull Player target) {
        UUID targetUUID = target.getUniqueId();
        LiWarnPlayer warnPlayer = super.getByUUID(targetUUID);
        if (warnPlayer == null) return -1;

        int countOfWarnings = warnPlayer.getWarnCount();
        if (--countOfWarnings <= 0) {
            super.clearSection(targetUUID);
            return 0;
        }

        warnPlayer.setWarnCount(countOfWarnings);

        List<PlayerWarning> warnings = warnPlayer.getWarnings();
        warnings.remove(warnings.size() - 1);
        super.setByUUID(targetUUID, warnPlayer);

        return countOfWarnings;
    }

}
