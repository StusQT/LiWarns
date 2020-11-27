package me.project.li_warns.gui.fake_book;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class FakeBook {
    private static final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    private static Method getHandle;
    private static Method openBook;
    private static Method asNMSCopy;
    private static Object enumHand_MAIN_HAND;
    private static boolean ready;

    static {
        try {
            getHandle = getCraftBukkitClass("entity", "CraftPlayer").getDeclaredMethod("getHandle");
            asNMSCopy = getCraftBukkitClass("inventory", "CraftItemStack").getDeclaredMethod("asNMSCopy", ItemStack.class);
            openBook = getNMSClass("EntityPlayer").getDeclaredMethod("openBook", getNMSClass("ItemStack"), getNMSClass("EnumHand"));
            enumHand_MAIN_HAND = getNMSClass("EnumHand").getEnumConstants()[0];
            ready = true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot prepare command 'warnlist'. Required class or method not found");
            ready = false;
        }
    }

    private static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + serverVersion + "." + name);
    }

    private static Class<?> getCraftBukkitClass(String pack, String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + serverVersion + "." + pack + "." + name);
    }

    public static boolean openBook(Player target, ItemStack book) {
        if (!ready) return false;
        boolean success;

        ItemStack oldItem = target.getInventory().getItemInMainHand();
        target.getInventory().setItemInMainHand(book);
        try {
            Object entityPlayer = getHandle.invoke(target);
            Object nmsBook = asNMSCopy.invoke(getCraftBukkitClass("entity", "CraftPlayer"), book);
            openBook.invoke(entityPlayer, nmsBook, enumHand_MAIN_HAND);
            success = true;
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            success = false;
        }
        target.getInventory().setItemInMainHand(oldItem);
        return success;
    }
}
