package yee.pltision.blasttech.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yee.pltision.blasttech.block.DisplayFurnaceBlock;

@Mixin(Items.class)
public class ItemsMixin {
    /*@Mutable
    @Final
    @Shadow
    public static Item BLAST_FURNACE = Items.registerItem(new ResourceLocation("blast_furnace"),new BlockItem(Blocks.BLAST_FURNACE,new Item.Properties())) ;
*/
/*    @Unique
    private static Item forgeFun$getBlastFurnace(){

        return Items.registerBlock(BlocksMixin.BLAST_FURNACE);
    }*/

    @Inject(method = "registerBlock(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/item/Item;",at=@At("HEAD"),cancellable = true)
    private static  void registerBlock(Block p_42806_, CallbackInfoReturnable<Item> cir){
//        System.out.println(p_42806_);
        if(p_42806_ instanceof DisplayFurnaceBlock) {
            System.out.println("yeeeeeeeeeee");
//            cir.setReturnValue(null);
        }
        if(p_42806_ instanceof BlastFurnaceBlock) {
            System.out.println("yeeeeeeeeeee3");
            cir.setReturnValue(null);
        }
    }

}
