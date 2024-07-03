package hyperdoot5.freezingwand.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

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
}
