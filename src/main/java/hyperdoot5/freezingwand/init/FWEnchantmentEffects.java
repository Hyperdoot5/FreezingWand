package hyperdoot5.freezingwand.init;

import com.mojang.serialization.MapCodec;
import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import hyperdoot5.freezingwand.enchantment.ApplyFrostedEffect;

public class FWEnchantmentEffects {

	public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, FreezingWandMod.MODID);

	public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<ApplyFrostedEffect>> APPLY_FROSTED = ENTITY_EFFECTS.register("apply_frosted", () -> ApplyFrostedEffect.CODEC);
}
