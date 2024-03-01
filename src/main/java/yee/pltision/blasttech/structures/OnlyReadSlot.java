package yee.pltision.blasttech.structures;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnlyReadSlot extends Slot {
    public OnlyReadSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    public boolean mayPlace(@NotNull ItemStack p_40231_) {
        return false;
    }

    public boolean mayPickup(@NotNull Player p_40228_) {
        return false;
    }

    public @NotNull ItemStack remove(int p_40227_) {
        return ItemStack.EMPTY;
    }
}
