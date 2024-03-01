package yee.pltision.blasttech.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yee.pltision.blasttech.block.DisplayedFurnaceEntity;

@Mixin(BlastFurnaceBlock.class)
public class BlastFurnaceBlockMixin {
    @Inject(method = "newBlockEntity",at=@At("HEAD"),cancellable = true)
    private void newBlockEntity(BlockPos p_152386_, BlockState p_152387_, CallbackInfoReturnable<BlockEntity> cir) {
        cir.setReturnValue(new DisplayedFurnaceEntity(p_152386_,p_152387_));
    }

    @Inject(method = "animateTick",at=@At("HEAD"),cancellable = true)
    private void animateTick(BlockState p_220818_, Level p_220819_, BlockPos p_220820_, RandomSource p_220821_, CallbackInfo ci){
        ci.cancel();
    }




}
