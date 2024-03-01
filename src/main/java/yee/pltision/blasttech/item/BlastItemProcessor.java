package yee.pltision.blasttech.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Set;

public interface BlastItemProcessor {
    default void attackEntity(AttackEntityEvent event, CompoundTag tag){}
    default void interactEntity(PlayerInteractEvent.EntityInteract event, CompoundTag tag){}
    default void criticalHitEntity(CriticalHitEvent event, CompoundTag tag){}
    default void tick(TickEvent.PlayerTickEvent event, Set<EquipmentSlot> slots, ServerPlayer player){}
}
