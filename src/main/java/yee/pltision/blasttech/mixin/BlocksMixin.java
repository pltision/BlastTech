package yee.pltision.blasttech.mixin;

import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yee.pltision.blasttech.block.DisplayFurnaceBlock;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Mutable
    @Final
    @Shadow
    public static final Block BLAST_FURNACE = Blocks.register("blast_furnace",new DisplayFurnaceBlock(
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F).lightLevel((p_50763_) -> p_50763_.getValue(BlockStateProperties.LIT) ? 13 : 0)));

    @Inject(method="register",at=@At("HEAD"),cancellable = true)
    private static void register(String p_50796_, Block block, CallbackInfoReturnable<Block> cir){

        if(block instanceof BlastFurnaceBlock){
//            BuiltInRegistries.BLOCK.stream().skip();
            System.out.println("yeeeeeeeeeee1");
            cir.setReturnValue(null);
        }
    }


}
