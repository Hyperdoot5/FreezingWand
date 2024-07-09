package hyperdoot5.freezingwand.client;

import hyperdoot5.freezingwand.client.particle.FrostParticle;
import hyperdoot5.freezingwand.client.renderer.entity.ThrownIceRenderer;
import hyperdoot5.freezingwand.init.FWEntities;
import hyperdoot5.freezingwand.init.FWParticleType;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;
import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = MODID)
public class FWClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        // Some client setup code
        DEBUG.info("Freezing Wand Acknowledges the Client");
        DEBUG.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FWEntities.THROWN_ICE.get(), ThrownIceRenderer::new);
	}

	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(FWParticleType.FROST.get(), FrostParticle.Factory::new);
	}
}
