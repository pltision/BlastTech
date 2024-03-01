package yee.pltision.blasttech.structures.blastfurnace;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import yee.pltision.blasttech.structures.OnlyReadSlot;

public class BlastFurnaceMenu extends AbstractContainerMenu {
    Container container;
    final int size=4;

    Player player;
    public BlastFurnaceMenu(BlastFurnaceEntity entity, Inventory inventory, int p_38852_) {
        super(MenuType.GENERIC_9x4, p_38852_);
        checkContainerSize(entity, size * 9);
        this.container = entity;
        entity.startOpen(inventory.player);
        int i = 0;

        for(int j = 0; j < this.size; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new OnlyReadSlot(entity, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
        }

        addSlotListener(new ContainerListener() {
            @Override
            public void slotChanged(@NotNull AbstractContainerMenu p_39315_, int p_39316_, @NotNull ItemStack p_39317_) {
                sendAllDataToRemote();
            }

            @Override
            public void dataChanged(@NotNull AbstractContainerMenu p_150524_, int p_150525_, int p_150526_) {
                sendAllDataToRemote();
            }

        });

    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player p_38874_) {
        return true;
    }

    public Container getContainer() {
        return this.container;
    }

    public int getRowCount() {
        return size;
    }
}
