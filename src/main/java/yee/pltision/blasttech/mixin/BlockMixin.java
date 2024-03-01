package yee.pltision.blasttech.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.*;

@Mixin(Block.class)
public class BlockMixin{
    @Mutable
    @Final
    @Shadow
    private final Holder.Reference<Block> builtInRegistryHolder = forgeFun$tryGetHolder(this);


    @Unique
    private static Holder.Reference<Block> forgeFun$tryGetHolder(Object obj){
        if(obj instanceof BlastFurnaceBlock) {
            System.out.println("yeeeeeeeeeee2");
            return null;
        }
        return BuiltInRegistries.BLOCK.createIntrusiveHolder((Block) obj);

    }
}
