package hyperdoot5.freezingwand;

import com.mojang.logging.LogUtils;
import hyperdoot5.freezingwand.init.*;
import hyperdoot5.freezingwand.network.ParticlePacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import java.util.Locale;

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

//ENSURE ALL SNOW_GUARDIAN INSTANCES ARE FROST
//ENSURE ALL CHILL AURA ARE FROZONE
//ENSURE ALL ICE BOMB ARE RENAME TO SOMETHING ELSE
//ENSURE EVERYTHING IS ADDED TO DATA GEN WHEN DONE
//REFERR TO OREMETER PACKET FOR POSSIBLE PACKET LAYOUT FOR WAND

@Mod(FreezingWandMod.MODID)
public class FreezingWandMod {
    public static final String MODID = "freezingwand";

    public static final Logger DEBUG = LogUtils.getLogger();

    public FreezingWandMod(IEventBus modEventBus, ModContainer modContainer) {

		FWItems.ITEMS.register(modEventBus);
		FWCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
		FWRecipes.RECIPE_SERIALIZERS.register(modEventBus); // Register to the mod event bus so custom recipes get registered
		FWStats.STATS.register(modEventBus);
        FWDataComponents.COMPONENTS.register(modEventBus);
		FWSounds.SOUNDS.register(modEventBus);
		FWEntities.ENTITIES.register(modEventBus);
		;
		FWMobEffects.MOB_EFFECTS.register(modEventBus);
		FWParticleType.PARTICLE_TYPES.register(modEventBus);
		FWEnchantmentEffects.ENTITY_EFFECTS.register(modEventBus);

		modEventBus.addListener(this::init);// Register the init method for modloading
		modEventBus.addListener(this::setupPackets);// Register the setupPackets method for modloading

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC); // Register our mod's ModConfigSpec so that FML can create and load the config file for us

        //Register for quirky server setup logs
//        NeoForge.EVENT_BUS.register(this);
    }

	public void setupPackets(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
		registrar.playToClient(ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
		//registrar.playToClient(UpdateThrownPacket.TYPE, UpdateThrownPacket.STREAM_CODEC, UpdateThrownPacket::handle);
		// registrar.playToServer(WipeOreMeterPacket.TYPE, WipeOreMeterPacket.STREAM_CODEC, WipeOreMeterPacket::handle);
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

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}

