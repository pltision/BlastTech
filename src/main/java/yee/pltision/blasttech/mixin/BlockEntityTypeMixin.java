package yee.pltision.blasttech.mixin;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yee.pltision.blasttech.block.DisplayedFurnaceEntity;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method="register",at=@At("HEAD"),cancellable = true)
    private static <T extends BlockEntity> void register(String p_58957_, BlockEntityType.Builder<T> p_58958_, CallbackInfoReturnable<BlockEntityType<T>> cir){
        if(p_58957_.equals("blast_furnace"))
            cir.setReturnValue((BlockEntityType<T>) Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "blast_furnace", BlockEntityType.Builder.of(DisplayedFurnaceEntity::new, Blocks.BLAST_FURNACE).build( Util.fetchChoiceType(References.BLOCK_ENTITY, "blast_furnace"))));
    }
}
