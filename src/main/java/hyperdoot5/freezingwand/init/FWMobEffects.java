package hyperdoot5.freezingwand.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.potions.FrostedEffect;

public class FWMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, FreezingWandMod.MODID);

	public static final DeferredHolder<MobEffect, MobEffect> FROSTY = MOB_EFFECTS.register("frosted", FrostedEffect::new);
}
