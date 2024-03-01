package yee.pltision.blasttech.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeRegistry.class,remap = false)
public abstract class ForgeRegistryMixin<V> {
    /*@Final
    @Shadow
    private BitSet availabilityMap;

    @Final
    @Shadow
    private int min,max;

    @Shadow
    public abstract V getRaw(ResourceLocation key);

    @Final
    @Shadow
    private static Logger LOGGER;

    @Final
    @Shadow
    public static Marker REGISTRIES;

    @Shadow
    public abstract int getID(V value);

    @Final
    @Shadow
    private boolean allowOverrides,hasWrapper;

    @Final
    @Shadow
    private BiMap<Integer, V> ids;

    @Final
    @Shadow
    private BiMap<ResourceLocation, V> names;

    @Final
    @Shadow
    private ResourceLocation name;

    @Final
    @Shadow
    public abstract boolean isLocked();

    @Final
    @Shadow
    private ResourceLocation defaultKey;

    @Shadow
    private V defaultValue;

    @Final
    @Shadow
    private BiMap<ResourceKey<V>, V> keys;

    @Final
    @Shadow
    private BiMap<Object, V> owners;

    @Final
    @Shadow
    private ResourceKey<Registry<V>> key;

    @Shadow
    protected abstract Holder.Reference<V> bindDelegate(ResourceKey<V> rkey, V value);

    @Final
    @Shadow
    private Multimap<ResourceLocation, V> overrides;

    @Final
    @Shadow
    private IForgeRegistry.AddCallback<V> add;

    @Final
    @Shadow
    private RegistryManager stage;

    @Final
    @Shadow
    public abstract ResourceLocation getKey(V value);

*//*    @Shadow abstract int add(int id, ResourceLocation key, V value);

    @Shadow abstract void validateKey();

    @Shadow abstract void onBindTags(Map<TagKey<V>, HolderSet.Named<V>> tags, Set<TagKey<V>> defaultedTags);*//*

    @Unique
    private static final Constructor<?> forgeFun$overrideOwnerConstructor;

    static {
        try {
            Class<?>overrideOwnerClass= Class.forName("net.minecraftforge.registries.ForgeRegistry$OverrideOwner");
            forgeFun$overrideOwnerConstructor =overrideOwnerClass.getConstructor(String.class,ResourceKey.class);
            forgeFun$overrideOwnerConstructor.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Inject(method = "Lnet/minecraftforge/registries/ForgeRegistry;add(ILnet/minecraft/resources/ResourceLocation;Ljava/lang/Object;Ljava/lang/String;)I",at=@At("HEAD"),cancellable = true)
    private void add(int id, ResourceLocation key, V value, String owner, CallbackInfoReturnable<Integer> cir){
//        cir.setReturnValue(forgeFun$add(id,key,value,owner));
    }

//    @Unique
//    int forgeFun$add(int id, ResourceLocation key, V value, String owner) {
//        Preconditions.checkNotNull(key, "Can't use a null-name for the registry, object %s.", value);
//        Preconditions.checkNotNull(value, "Can't add null-object to the registry, name %s.", key);
//
//        int idToUse = id;
//        if (idToUse < 0 || availabilityMap.get(idToUse))
//            idToUse = availabilityMap.nextClearBit(min);
//
//        if (idToUse > max)
//            throw new RuntimeException(String.format(Locale.ENGLISH, "Invalid id %d - maximum id range exceeded.", idToUse));
//
//        V oldEntry = getRaw(key);
//        if (oldEntry == value) { // already registered, return prev registration's id
//            LOGGER.warn(REGISTRIES,"Registry {}: The object {} has been registered twice for the same name {}.", this.name, value, key);
//            return this.getID(value);
//        }
//
//        if (oldEntry != null) { // duplicate name
//            if (!this.allowOverrides)
//                throw new IllegalArgumentException(String.format(Locale.ENGLISH, "The name %s has been registered twice, for %s and %s.", key, getRaw(key), value));
//            if (owner == null)
//                throw new IllegalStateException(String.format(Locale.ENGLISH, "Could not determine owner for the override on %s. Value: %s", key, value));
//            LOGGER.debug(REGISTRIES,"Registry {} Override: {} {} -> {}", this.name, key, oldEntry, value);
//            idToUse = this.getID(oldEntry);
//        }
//
//        Integer foundId = this.ids.inverse().get(value); //Is this ever possible to trigger with otherThing being different?
//        if (foundId != null) {
//            V otherThing = this.ids.get(foundId);
//            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "The object %s{%x} has been registered twice, using the names %s and %s. (Other object at this id is %s{%x})", value, System.identityHashCode(value), getKey(value), key, otherThing, System.identityHashCode(otherThing)));
//        }
//
//        if (isLocked())
//            throw new IllegalStateException(String.format(Locale.ENGLISH, "The object %s (name %s) is being added too late.", value, key));
//
//        /*if (defaultKey != null && defaultKey.equals(key)) {
//            if (this.defaultValue != null)
//                throw new IllegalStateException(String.format(Locale.ENGLISH, "Attemped to override already set default value. This is not allowed: The object %s (name %s)", value, key));
//            this.defaultValue = value;
//        }*/
//
//        ResourceKey<V> rkey = ResourceKey.create(this.key, key);
//        this.names.put(key, value);
//        this.keys.put(rkey, value);
//        this.ids.put(idToUse, value);
//        this.availabilityMap.set(idToUse);
//        try {
//            this.owners.put(forgeFun$overrideOwnerConstructor.newInstance(owner == null ? key.getNamespace() : owner, rkey), value);
//
//        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
//            System.out.println("---");
//            e.printStackTrace();
//            System.out.println("---");
//            throw new RuntimeException(e);
//        }
//
//        if (hasWrapper) {
//            bindDelegate(rkey, value);
//            if (oldEntry != null) {
//                if (!this.overrides.get(key).contains(oldEntry))
//                    this.overrides.put(key, oldEntry);
//                this.overrides.get(key).remove(value);
//            }
//        }
//
//        if (this.add != null)
//            this.add.onAdd((IForgeRegistryInternal<V>) this, this.stage, idToUse, rkey, value, oldEntry);
//
//        LOGGER.trace(REGISTRIES,"Registry {} add: {} {} {} (req. id {})", this.name, key, idToUse, value, id);
//
//        return idToUse;
//    }
}
