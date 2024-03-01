/*
package yee.pltision.forgefun.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.w3c.dom.events.Event;

public record LazyItemProcessor(
        LazyConsumer<?super AttackEntityEvent> attackEntity,
        LazyConsumer<?super PlayerInteractEvent.EntityInteract>interactEntity,
        LazyConsumer<?super CriticalHitEvent>criticalHitEntity
) implements BlastItemProcessor {

    @FunctionalInterface
    public interface LazyConsumer<Event>{
        void accept(Event event, CompoundTag tag);
    }
    public static final LazyConsumer<Object> EMPTY_PROCESSOR=(event, tag)->{};

    @Override
    public void attackEntity(AttackEntityEvent event, CompoundTag blastItemTag) {
        attackEntity.accept(event,blastItemTag);
    }

    @Override
    public void interactEntity(PlayerInteractEvent.EntityInteract event, CompoundTag blastItemTag) {
        interactEntity.accept(event,blastItemTag);

    }

    @Override
    public void criticalHitEntity(CriticalHitEvent event, CompoundTag blastItemTag) {
        criticalHitEntity.accept(event,blastItemTag);

    }



}
*/
