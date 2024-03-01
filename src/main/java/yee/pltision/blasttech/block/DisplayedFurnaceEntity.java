package yee.pltision.blasttech.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import yee.pltision.blasttech.structures.StructureEntity;

public class DisplayedFurnaceEntity extends BlockEntity {

    @Nullable ResourceLocation type;
    @Nullable StructureEntity entity;
    public DisplayedFurnaceEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityType.BLAST_FURNACE, p_155229_, p_155230_);
    }




}
