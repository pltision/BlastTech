package yee.pltision.blasttech.item;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import yee.pltision.blasttech.ExampleMod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber
public class ItemEvents {

    public static final ResourceKey<Registry<BlastItemProcessor>> REGISTRY_RESOURCE_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(ExampleMod.MODID, "blast_item"));

    public static final Map<ResourceKey<BlastItemProcessor>, BlastItemProcessor> BLAST_ITEM_REGISTRY = new HashMap<>();

    public static void registry(ResourceLocation location, BlastItemProcessor processor) {
        BLAST_ITEM_REGISTRY.put(ResourceKey.create(REGISTRY_RESOURCE_KEY, location), processor);
    }

    private static void registry(String location, BlastItemProcessor processor) {
        registry(new ResourceLocation(ExampleMod.MODID, location), processor);
    }

    public static void init(){
        registry("stickable_end_rod", new StickableEndRod());
    }

    public static final String BLAST_ITEM_TAG ="BlastItem";

    public static final String KILL_WHEN_DISMOUNT="KillWhenDismount";
    public static final String STICKABLE_END_ROD_TAG="blasttech:stickable_end_rod";
    public record TagAndProcessor(BlastItemProcessor processor,ResourceKey<BlastItemProcessor> key,CompoundTag tag){
        public static @Nullable TagAndProcessor create(ItemStack item){
            CompoundTag tag= item.getTagElement(BLAST_ITEM_TAG);
            if(tag==null)return null;
            String id=tag.getString("id");
            ResourceLocation location=ResourceLocation.tryParse(id);
            if(location==null)return null;
            ResourceKey<BlastItemProcessor> key=ResourceKey.create(REGISTRY_RESOURCE_KEY, location);
            BlastItemProcessor processor=BLAST_ITEM_REGISTRY.get(key);
            if(processor==null)return null;
            return new TagAndProcessor(processor,key,tag);
        }
    }

    @SubscribeEvent
    public static void attackEntity(AttackEntityEvent event){
        TagAndProcessor tagAndProcessor= TagAndProcessor.create(event.getEntity().getMainHandItem());
        if(tagAndProcessor!=null)
            tagAndProcessor.processor.attackEntity(event,tagAndProcessor.tag);
    }

    @SubscribeEvent
    public static void interactEntity(PlayerInteractEvent.EntityInteract event){
        TagAndProcessor tagAndProcessor= TagAndProcessor.create(event.getEntity().getMainHandItem());
        if(tagAndProcessor!=null)
            tagAndProcessor.processor.interactEntity(event,tagAndProcessor.tag);
    }

    @SubscribeEvent
    public static void criticalHitEntity(CriticalHitEvent event){
        TagAndProcessor tagAndProcessor= TagAndProcessor.create(event.getEntity().getMainHandItem());
        if(tagAndProcessor!=null)
            tagAndProcessor.processor.criticalHitEntity(event,tagAndProcessor.tag);
    }

    /**
     * 用于给玩家穿着或手持的BlastItem在每刻执行代码。
     */
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
//        System.out.println("yee");
        if(event.player instanceof ServerPlayer player){
            Map<BlastItemProcessor, Set<EquipmentSlot>> triggedMap=new HashMap<>(); //用于统计需要触发的物品
            for(EquipmentSlot slot:EquipmentSlot.values()){
//                System.out.println(player.getItemBySlot(slot));
                TagAndProcessor processor= TagAndProcessor.create(player.getItemBySlot(slot));
                if(processor!=null){
                    Set<EquipmentSlot> set =
                            triggedMap.computeIfAbsent(processor.processor, k -> new HashSet<>());    //统计这种物品同时存在哪些槽位
                    set.add(slot);
                }
            }
            for(Map.Entry<BlastItemProcessor, Set<EquipmentSlot>> entry:triggedMap.entrySet()){
                entry.getKey().tick(event,entry.getValue(),player);    //给物品触发tick事件
            }
            player.getCapability(TickAccepts.TICK_ACCEPTS).ifPresent(cap->cap.accept(event));   //执行玩家需要每刻执行的其他东西
        }
    }

    @SubscribeEvent
    public static void vanillaEvents(VanillaGameEvent event){
        GameEvent gameEvent=event.getVanillaEvent();
        if(gameEvent==GameEvent.ENTITY_DISMOUNT){
            if(event.getContext().sourceEntity()instanceof ArmorStand stand
            &&stand.getTags().contains(KILL_WHEN_DISMOUNT)){
                stand.kill();
            }
        }
    }

}
