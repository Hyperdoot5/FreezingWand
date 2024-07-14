package hyperdoot5.freezingwand.events;


import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import hyperdoot5.freezingwand.enchantment.ApplyFrostedEffect;
import hyperdoot5.freezingwand.init.*;

import java.util.Optional;

@EventBusSubscriber(modid = FreezingWandMod.MODID)
public class EntityEvents {

	@SubscribeEvent
	public static void onLivingHurtEvent(LivingDamageEvent event) {
		LivingEntity living = event.getEntity();
		float damage = event.getAmount();
		DamageSource source = event.getSource();

		Optional.ofNullable(living.getEffect(FWMobEffects.FROSTY)).ifPresent(mobEffectInstance -> {
			if (source.typeHolder().is(DamageTypes.FREEZE)) {
				event.setAmount(damage + (float) (mobEffectInstance.getAmplifier() / 2));
			} else if (source.typeHolder().is(DamageTypeTags.IS_FIRE)) {
				living.removeEffect(FWMobEffects.FROSTY);
				if (mobEffectInstance.getAmplifier() >= 0) living.addEffect(mobEffectInstance);
			}
		});
	}
}
