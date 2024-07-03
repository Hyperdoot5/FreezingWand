package hyperdoot5.freezingwand.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;
import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FWKeyMapHandler {
    // Key mapping to change wand attunement
    // Key mapping is lazily initialized so it doesn't exist until it is registered
    public static final Lazy<KeyMapping> FREEZING_WAND_ATTUNEMENT = Lazy.of(() -> new KeyMapping(
            "key.freezingwand.attunement",
            KeyConflictContext.IN_GAME,
//            KeyModifier.SHIFT, // Default mapping requires shift to be held down
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_APOSTROPHE, // Default mouse input is the apostrophe button
            "key.categories.freezingwand.freezingwand"));

    // Event is on the mod event bus only on the physical client
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(FREEZING_WAND_ATTUNEMENT.get());
    }
}
