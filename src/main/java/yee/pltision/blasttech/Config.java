package yee.pltision.blasttech;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_TICK_EVENT = BUILDER
            .comment("Enable player tick event.")
            .define("playerTick", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableTickEvent;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        enableTickEvent = ENABLE_TICK_EVENT.get();

    }
}
