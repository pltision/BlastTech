package yee.pltision.blasttech.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import yee.pltision.blasttech.ExampleMod;
import yee.pltision.blasttech.structures.blastfurnace.BlastFurnaceEntity;

import java.util.HashMap;
import java.util.Map;

import static yee.pltision.blasttech.block.DisplayFurnaceBlock.FACING;

public class BlastStructures {
    public static final ResourceKey<Registry<StructureType<?>>> REGISTRY_RESOURCE_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(ExampleMod.MODID, "structure_type"));

    //md自定义ForgeRegistry讲的不明不白还找不到教程
    public static final Map<ResourceKey<StructureType<?>>, StructureType<?>> STRUCTURE_TYPE_REGISTRY = new HashMap<>();

    public static void registry(ResourceLocation location, StructureType<?> type) {
        STRUCTURE_TYPE_REGISTRY.put(ResourceKey.create(REGISTRY_RESOURCE_KEY, location), type);
    }

    private static void registry(String location, StructureType<?> type) {
        registry(new ResourceLocation(ExampleMod.MODID, location), type);
    }

    public static void init() {
        registry("blast_furnace", BlastStructures::blastFurnace);
    }

    public static BlastFurnaceEntity blastFurnace(BlockState state, Level level, BlockPos pos, BlockState replaced) {
        if (!state.hasProperty(FACING)) return null;

        Direction facing = state.getValue(FACING);
        BlockPos origin = pos.relative(facing.getOpposite());
        BlockState stoneBricks = Blocks.STONE_BRICKS.defaultBlockState();

        if (BlastFurnaceEntity.testButton(level, origin, facing, stoneBricks))
            return new BlastFurnaceEntity();
        else return null;
    }


}
