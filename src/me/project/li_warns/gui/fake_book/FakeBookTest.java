package me.project.li_warns.gui.fake_book;

import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.EnumHand;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

/**
 * This is a FakeBook class without reflection
 * Target version: v1_16_R2 (Minecraft 1.16.2)
 */
public class FakeBookTest {
    @TestOnly
    public static void openBook(@NotNull Player target, @NotNull ItemStack book) {
        EntityPlayer ePlayer = ((CraftPlayer) target).getHandle();
        ItemStack oldItem = target.getInventory().getItemInMainHand();

        target.getInventory().setItemInMainHand(book);
        ePlayer.openBook(CraftItemStack.asNMSCopy(book), EnumHand.MAIN_HAND);

        target.getInventory().setItemInMainHand(oldItem);
    }
}
