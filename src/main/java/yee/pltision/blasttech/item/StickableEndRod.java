package yee.pltision.blasttech.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import yee.pltision.blasttech.ExampleMod;

import java.util.Set;

import static yee.pltision.blasttech.item.ItemEvents.REGISTRY_RESOURCE_KEY;

public class StickableEndRod implements BlastItemProcessor{
    public static final ResourceKey<BlastItemProcessor> STICKABLE_END_ROD_KEY=ResourceKey.create(REGISTRY_RESOURCE_KEY, new ResourceLocation(ExampleMod.MODID,"stickable_end_rod"));
    public static final String STICKABLE_END_ROD=ExampleMod.MODID+":stickable_end_rod";
    @Override
    public void criticalHitEntity(CriticalHitEvent event, CompoundTag blastItemTag) {
//            event.getEntity().isCrouching()
        if(event.getEntity()instanceof ServerPlayer player&&event.getTarget() instanceof ServerPlayer target){
            ItemStack item=target.getInventory().armor.get(1);

            if(!target.getPassengers().isEmpty()){
                player.server.sendSystemMessage(Component.literal("目标已被实体乘骑"));
                return;
            }
            if(item.getItem().getAllEnchantments(item).containsKey(Enchantments.BINDING_CURSE)){
                player.server.sendSystemMessage(Component.literal("无法移除目标的护腿"));
            }
            else {
                blastItemTag.putUUID("from",target.getUUID());
                target.getInventory().armor.set(1,event.getEntity().getMainHandItem().copyWithCount(1));
                event.getEntity().getMainHandItem().shrink(1);
                target.addItem(item);
            }

        }
    }

    @Override
    public void tick(TickEvent.PlayerTickEvent event, Set<EquipmentSlot> slots, ServerPlayer player) {
        if(slots.contains(EquipmentSlot.LEGS)){
            if(player.getPassengers().isEmpty()){
                ArmorStand armorStand=new ArmorStand(player.level(),player.getX(),player.getY(),player.getZ());
                {
                    CompoundTag tag = new CompoundTag();
                    tag.putBoolean("Small", true);   //不能直接设置小型(恼
                    armorStand.readAdditionalSaveData(tag);

                    armorStand.setInvisible(true);
                    armorStand.setInvulnerable(true);

                    armorStand.addTag(STICKABLE_END_ROD);
                    armorStand.addTag(ItemEvents.KILL_WHEN_DISMOUNT);

                    ItemStack endRod=new ItemStack(Items.END_ROD);
                    tag=new CompoundTag();
                    tag.putString("id",STICKABLE_END_ROD);
                    endRod.addTagElement("BlastItem",tag);
                    armorStand.setItemSlot(EquipmentSlot.HEAD, endRod);
                }
                player.level().addFreshEntity(armorStand);
                armorStand.startRiding(player, true);
                player.getCapability(TickAccepts.TICK_ACCEPTS).ifPresent(cap->cap.accepts.putIfAbsent(this,this::checkEndRod));
                player.connection.send(new ClientboundSetPassengersPacket(player));
            }

            float healthFactor= player.getHealth()/ player.getMaxHealth();
//            System.out.println(player.tickCount);
            if(player.tickCount%(41-((int)(healthFactor*20))*2)==0){
                player.hurt(player.damageSources().genericKill(),healthFactor*2+0.2f);
            }
            endRodEffects(player);
        }

    }

    public boolean checkEndRod(TickEvent.PlayerTickEvent event){
        ItemEvents.TagAndProcessor processor= ItemEvents.TagAndProcessor.create(event.player.getItemBySlot(EquipmentSlot.LEGS));

        if(event.player.getPassengers().isEmpty()) return true;
        Entity passenger=event.player.getPassengers().get(0);
        if(processor==null||processor.processor()!=this){
            if(passenger instanceof ArmorStand&&passenger.getTags().contains(STICKABLE_END_ROD)){
                passenger.kill();
                if(event.player instanceof ServerPlayer player)
                    player.connection.send(new ClientboundSetPassengersPacket(player));
            }
            return true;
        }
        else{
            if(passenger instanceof ArmorStand){
                passenger.setYRot(event.player.getYRot());
                passenger.setYHeadRot(event.player.getYRot());
                passenger.setOldPosAndRot();
//                System.out.println(event.player.getYRot());
            }
            return false;
        }
    }
    public static void endRodEffects(final Player player){
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,12*20,1,true,true));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,6*20,2,true,true));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,6*20,1,true,true));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,6*20,1,true,true));

    }
}
