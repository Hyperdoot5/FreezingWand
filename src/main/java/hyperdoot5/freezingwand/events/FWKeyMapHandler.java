package hyperdoot5.freezingwand.events;

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

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FWKeyMapHandler {
    // Key mapping to change wand attunement
    // Key mapping is lazily initialized so it doesn't exist until it is registered
	public static final Lazy<KeyMapping> ATTUNEMENT_KEYMAP = Lazy.of(() -> new KeyMapping(
            "key.freezingwand.attunement",
            KeyConflictContext.IN_GAME,
//            KeyModifier.SHIFT, // Default mapping requires shift to be held down
		InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
		GLFW.GLFW_KEY_BACKSLASH, // Default mouse input is the apostrophe button
		"key.categories.freezingwand.freezingwand"));

	public static final Lazy<KeyMapping> BASIC_OVERRIDE_KEYMAP = Lazy.of(() -> new KeyMapping(
		"key.freezingwand.basic",
		KeyConflictContext.IN_GAME,
//            KeyModifier.SHIFT, // Default mapping requires shift to be held down
		InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
		GLFW.GLFW_KEY_RIGHT_BRACKET, // Default mouse input is the apostrophe button
		"key.categories.freezingwand.freezingwand"));

	public static final Lazy<KeyMapping> ICE_OVERRIDE_KEYMAP = Lazy.of(() -> new KeyMapping(
		"key.freezingwand.ice",
		KeyConflictContext.IN_GAME,
//            KeyModifier.SHIFT, // Default mapping requires shift to be held down
		InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
		GLFW.GLFW_KEY_LEFT_BRACKET, // Default mouse input is the apostrophe button
		"key.categories.freezingwand.freezingwand"));

	public static final Lazy<KeyMapping> PACKED_ICE_OVERRIDE_KEYMAP = Lazy.of(() -> new KeyMapping(
		"key.freezingwand.packed_ice",
		KeyConflictContext.IN_GAME,
//            KeyModifier.SHIFT, // Default mapping requires shift to be held down
		InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
		GLFW.GLFW_KEY_SEMICOLON, // Default mouse input is the apostrophe button
		"key.categories.freezingwand.freezingwand"));

	public static final Lazy<KeyMapping> BLUE_ICE_OVERRIDE_KEYMAP = Lazy.of(() -> new KeyMapping(
		"key.freezingwand.blue_ice",
		KeyConflictContext.IN_GAME,
//            KeyModifier.SHIFT, // Default mapping requires shift to be held down
		InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
		GLFW.GLFW_KEY_APOSTROPHE, // Default mouse input is the apostrophe button
		"key.categories.freezingwand.freezingwand"));

    // Event is on the mod event bus only on the physical client
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
		event.register(ATTUNEMENT_KEYMAP.get());
		event.register(BASIC_OVERRIDE_KEYMAP.get());
		event.register(ICE_OVERRIDE_KEYMAP.get());
		event.register(PACKED_ICE_OVERRIDE_KEYMAP.get());
		event.register(BLUE_ICE_OVERRIDE_KEYMAP.get());
    }
}
