package hyperdoot5.freezingwand;

import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import hyperdoot5.freezingwand.init.*;
import hyperdoot5.freezingwand.network.AttunementChangePacket;
import hyperdoot5.freezingwand.network.ParticlePacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import java.util.Locale;

import static net.neoforged.fml.loading.FMLEnvironment.dist;

/*
* NOTICE
*
* Eventhough in the world of programming there is a lot of copy & paste of what works,
 * I would like to give credit to the TwilightForestMod and its creators for its inspiration, many solutions, and general mod structuring,
*
* I saw what worked and what was readable and decided if it aint broke, dont fix it
* as such many naming schemes are similar
*
*/

// OPTIONAL ADDITIONS:
// Add config functionality (low priority -> MOD is Good Enough)

@Mod(FreezingWandMod.MODID)
public class FreezingWandMod {
    public static final String MODID = "freezingwand";
    public static final Logger DEBUG = LogUtils.getLogger();

	public FreezingWandMod(IEventBus modEventBus, Dist dist) {
//		Reflection.initialize(ConfigSetup.class);
//		if (dist.isClient()) {
//			FWClientSetup.init(modEventBus);
//		}

		FWItems.ITEMS.register(modEventBus);
		FWCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
		FWRecipes.RECIPE_SERIALIZERS.register(modEventBus); // Register to the mod event bus so custom recipes get registered
		FWStats.STATS.register(modEventBus);
        FWDataComponents.COMPONENTS.register(modEventBus);
		FWSounds.SOUNDS.register(modEventBus);
		FWEntities.ENTITIES.register(modEventBus);
		FWMobEffects.MOB_EFFECTS.register(modEventBus);
		FWParticleType.PARTICLE_TYPES.register(modEventBus);
		FWEnchantmentEffects.ENTITY_EFFECTS.register(modEventBus);

		modEventBus.addListener(this::init);// Register the init method for modloading
		modEventBus.addListener(this::setupPackets);// Register the setupPackets method for modloading
//		modEventBus.addListener(ConfigSetup::loadConfigs);
//		modEventBus.addListener(ConfigSetup::reloadConfigs);
//		NeoForge.EVENT_BUS.addListener(ConfigSetup::sync);
    }

	public void setupPackets(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
		registrar.playToClient(ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
		registrar.playToServer(AttunementChangePacket.TYPE, AttunementChangePacket.STREAM_CODEC, AttunementChangePacket::handle);
	}
    private void init(final FMLCommonSetupEvent event) {
		DEBUG.info("Freezing Wand Setup Initiated");
        event.enqueueWork(FWStats::init); // Not threadsafe, put on main thread
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}

