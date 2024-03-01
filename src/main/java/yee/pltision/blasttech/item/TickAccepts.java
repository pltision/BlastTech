package yee.pltision.blasttech.item;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yee.pltision.blasttech.ExampleMod;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber
public class TickAccepts implements ICapabilityProvider {
    public static final Capability<TickAccepts> TICK_ACCEPTS = CapabilityManager.get(new CapabilityToken<>(){});
    @SubscribeEvent
    public static void registryCapability(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject()instanceof Player){
            event.addCapability(new ResourceLocation(ExampleMod.MODID,"tick_accepts"), new TickAccepts());
        }
    }
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(TickAccepts.class);
    }

    LazyOptional<TickAccepts> optional;
    public final Map<?super Object, Function<TickEvent.PlayerTickEvent,Boolean>> accepts;

    public TickAccepts(){
        accepts =new HashMap<>();
        optional=LazyOptional.of(()->this);
    }

    public void accept(TickEvent.PlayerTickEvent event){
//        System.out.println("喵喵");
        accepts.entrySet().removeIf(functionEntry -> functionEntry.getValue().apply(event));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == TICK_ACCEPTS ? optional.cast():LazyOptional.empty();
    }
}
