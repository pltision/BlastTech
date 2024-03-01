package yee.pltision.blasttech.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(targets="net.minecraftforge.registries.NamespacedWrapper",remap = false)
public abstract class NamespacedWrapperMixin<T> extends MappedRegistry<T> {

    @Shadow
    private boolean frozen;

    @Shadow
    private Map<ResourceLocation, Holder.Reference<T>> holdersByName;

    @Shadow
    private RegistryManager stage;

    public NamespacedWrapperMixin(ResourceKey p_249899_, Lifecycle p_252249_) {
        super(p_249899_, p_252249_);
    }

    @Inject(method = "freeze",at=@At("HEAD"),cancellable = true)
    private void freeze(CallbackInfoReturnable<Registry<T>> cir){
        this.frozen = true;
        List<ResourceLocation> unregistered = this.holdersByName.entrySet().stream()
                .filter(e -> !e.getValue().isBound())
                .map(Map.Entry::getKey).sorted().toList();

        if (this.unregisteredIntrusiveHolders != null && this.unregisteredIntrusiveHolders.values().stream().anyMatch(r -> !r.isBound() && r.getType() == Holder.Reference.Type.INTRUSIVE)) {
//            throw new IllegalStateException("Some intrusive holders were not registered: " + this.unregisteredIntrusiveHolders.values() + " Hint: Did you register all your registry objects? Registry stage: " + stage.getName());
        }

        cir.setReturnValue( this);
    }
}
