package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FWSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, FreezingWandMod.MODID);

	public static final DeferredHolder<SoundEvent, SoundEvent> WAND_ATTUNEMENT = createEvent("item.freezingwand.attunement");
	public static final DeferredHolder<SoundEvent, SoundEvent> BOLT_FIRED = createEvent("entity.freezingwand.ice.bolt");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_PLACED = createEvent("block.freezingwand.ice.placed");
	public static final DeferredHolder<SoundEvent, SoundEvent> ICE_FIRED = createEvent("entity.freezingwand.ice.fired");
	public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_COLLECTED = createEvent("block.freezingwand.ice.collected");

	private static DeferredHolder<SoundEvent, SoundEvent> createEvent(String sound) {
		return SOUNDS.register(sound, () -> SoundEvent.createVariableRangeEvent(FreezingWandMod.prefix(sound)));
	}
}
