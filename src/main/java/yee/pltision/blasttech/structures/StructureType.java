package yee.pltision.blasttech.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface StructureType<E extends StructureEntity> {
    /**
     * 在方块被放置时调用。
     * 尝试创建结构，若成功的话你还可以同时替换多方块结构中的方块。
     * @return 如果结构正确就创建一个，否则返回null
     */
    E tryCreate(BlockState state, Level level, BlockPos pos, BlockState replaced);



}
