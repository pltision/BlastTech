package yee.pltision.blasttech;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;
import yee.pltision.blasttech.item.ItemEvents;
import yee.pltision.blasttech.structures.BlastStructures;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MODID)
public class ExampleMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "blasttech";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,MODID);
    public static final DeferredRegister<Block> BLOCKS =DeferredRegister.create(ForgeRegistries.BLOCKS,"minecraft");
    public static final DeferredRegister<Item> ITEMS =DeferredRegister.create(ForgeRegistries.ITEMS,"minecraft");


    /*public static final RegistryObject<Block> BLAST_FURNACE=BLOCKS.register("blast_furnace",()->
            BlocksMixin.BLAST_FURNACE= new StructureFurnace(
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F).lightLevel((p_50763_)
                    -> p_50763_.getValue(BlockStateProperties.LIT) ? 13 : 0)));

    public static final RegistryObject<Item> BLAST_FURNACE_ITEM=ITEMS.register("blast_furnace",()->
            ItemsMixin.BLAST_FURNACE=new BlockItem(BLAST_FURNACE.get(),new Item.Properties()));*/

//    public static final RegistryObject<BlockEntityType<?>> STRUCTURE_FURNACE_ENTITY=BLOCK_ENTITY_TYPE.register("structure_furnace",()->BlockEntityType.Builder.of(DisplayedFurnaceEntity::new, Blocks.STONE_BRICKS).build(null));
    public ExampleMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        BLOCK_ENTITY_TYPE.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        BlastStructures.init();
        ItemEvents.init();

        /*if(Config.enableTickEvent)
            modEventBus.addListener(ItemEvents::playerTick);*/

    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
