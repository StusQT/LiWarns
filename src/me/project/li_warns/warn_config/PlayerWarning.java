package me.project.li_warns.warn_config;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("PlayerWarning")
public class PlayerWarning implements ConfigurationSerializable {
    protected final String adminName;
    protected final String cause;
    protected final LocalDateTime dateTime;

    public PlayerWarning(@NotNull String adminName, @NotNull String cause) {
        this.adminName = adminName;
        this.cause = cause;
        dateTime = LocalDateTime.now();
    }

    protected PlayerWarning(@NotNull String adminName, @NotNull String cause, @NotNull LocalDateTime dateTime) {
        this.adminName = adminName;
        this.cause = cause;
        this.dateTime = dateTime;
    }

    public static PlayerWarning deserialize(@NotNull Map<String, Object> map) {
        return new PlayerWarning((String) map.get("adminName"), (String) map.get("cause"), LocalDateTime.parse(map.get("time").toString()));
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("adminName", adminName);
        map.put("cause", cause);
        map.put("time", dateTime.toString());

        return map;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getCause() {
        return cause;
    }

    public LocalDateTime getTime() {
        return dateTime;
    }
}
