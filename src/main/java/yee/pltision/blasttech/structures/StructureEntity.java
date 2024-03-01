package yee.pltision.blasttech.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.INBTSerializable;
import yee.pltision.blasttech.block.DisplayedFurnaceEntity;

public interface StructureEntity extends INBTSerializable<CompoundTag> {

    /**
     * 当Block的use调用时也在这调用一次
     */
    InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result);

    default void tick(Level level, BlockPos pos, BlockState state, DisplayedFurnaceEntity entity){}

    default boolean createTicker(){return false;}

}
