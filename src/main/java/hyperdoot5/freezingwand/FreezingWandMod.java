package hyperdoot5.freezingwand;

import com.mojang.logging.LogUtils;
import hyperdoot5.freezingwand.init.FWRecipes;
import hyperdoot5.freezingwand.util.FWAnvilHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import static hyperdoot5.freezingwand.init.FWCreativeTabs.CREATIVE_MODE_TABS;
import static hyperdoot5.freezingwand.init.FWItems.ITEMS;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FreezingWandMod.MODID)
public class FreezingWandMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "freezingwand";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FreezingWandMod(IEventBus modEventBus, ModContainer modContainer)
    {

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod even bus so Custom Recipes get registered
        FWRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(new FWAnvilHandler());

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Freezing Wand Setup Initiated");

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

/*
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Freezing Wand Acknowledges the Server");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("Freezing Wand Acknowledges the Client");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

    }
 */
}
