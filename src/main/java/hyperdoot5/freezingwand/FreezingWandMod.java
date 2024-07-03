package hyperdoot5.freezingwand;

import com.mojang.logging.LogUtils;
import hyperdoot5.freezingwand.init.*;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;

import java.util.Locale;

import static hyperdoot5.freezingwand.client.FWKeyMapHandler.FREEZING_WAND_ATTUNEMENT;

/*
* NOTICE
*
* Eventhough in the world of programming there is a lot of copy & paste of what works,
* I would like to give credit to the TwilightForestMod for its inspiration, many solutions, and general mod structuring,
*
* I saw what worked and what was readable and decided if it aint broke, dont fix it
* as such many naming schemes are similar
*
*/

@Mod(FreezingWandMod.MODID)
public class FreezingWandMod {
    public static final String MODID = "freezingwand";

    public static final Logger DEBUG = LogUtils.getLogger();

    public FreezingWandMod(IEventBus modEventBus, ModContainer modContainer) {

        // Register the init method for modloading
        modEventBus.addListener(this::init);

        FWItems.ITEMS.register(modEventBus); // Register the Deferred Register to the mod event bus so items get registered
        FWCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus); // Register the Deferred Register to the mod event bus so tabs get registered
        FWRecipes.RECIPE_SERIALIZERS.register(modEventBus); // Register the Deferred Register to the mod event bus so custom recipes get registered
        FWStats.STATS.register(modEventBus); // Register the Deffered Register to the mod event bus so custom stats get registered
        FWDataComponents.COMPONENTS.register(modEventBus);



        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC); // Register our mod's ModConfigSpec so that FML can create and load the config file for us

        //Register for quirky server setup logs
//        NeoForge.EVENT_BUS.register(this);
    }

    private void init(final FMLCommonSetupEvent event) {
        DEBUG.info("Freezing Wand Common Setup Initiated");
        event.enqueueWork(FWStats::init); // Not threadsafe, put on main thread
    }
//
//     The constructor for the mod class is the first code that is run when your mod is loaded.
//     FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
//     You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        // Do something when the server starts
//        DEBUG.info("Freezing Wand Acknowledges the Server");
//    }

        // works as intended
        private static void checkAttunment(ClientTickEvent.Post event){
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            while (FREEZING_WAND_ATTUNEMENT.get().consumeClick()) {
                if (player != null && player.getMainHandItem().getItem() instanceof FreezingWandItem) {
                    DEBUG.info("Model...");
                }
            }
        }
        // does not work as intended
        private static float getAttunment(){
            DEBUG.info("...Changed");
            return 1;
        }
//    public void setupPackets(RegisterPayloadHandlersEvent event){
//        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
//        registrar.playToClient(UpdateThrownPacket.TYPE, UpdateThrownPacket.STREAM_CODEC, UpdateThrownPacket::handle);
//        registrar.playToServer(WipeOreMeterPacket.TYPE, WipeOreMeterPacket.STREAM_CODEC, WipeOreMeterPacket::handle);
//        registrar.playToClient(ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
//    }
    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}

