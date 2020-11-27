package me.project.li_warns.plugin_logger;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PluginLogger {
    protected final BufferedWriter bufferedWriter;
    protected String prefix;
    protected DateTimeFormatter formatter;

    public PluginLogger(@NotNull File log, @Nullable String prefix) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(log, true));
        this.prefix = prefix;
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    public void log(String action, String text) {
        try {
            bufferedWriter.write(generateWritablePrefix(action) + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(Enum<?> action, String text) {
        try {
            bufferedWriter.write(generateWritablePrefix(action) + text);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String generateWritablePrefix(@NotNull String action) {
        return prefix == null ? String.format("[%s] [%s]: ", formatter.format(LocalTime.now()), action)
                : String.format("[%s] [%s] [%s]: ", formatter.format(LocalTime.now()), prefix, action);
    }

    protected String generateWritablePrefix(@NotNull Enum<?> action) {
        return prefix == null ? String.format("[%s] [%s]: ", formatter.format(LocalTime.now()), action.toString())
                : String.format("[%s] [%s] [%s]: ", formatter.format(LocalTime.now()), prefix, action.toString());
    }

    public void close() throws IOException {
        bufferedWriter.close();
        prefix = null;
    }
}
