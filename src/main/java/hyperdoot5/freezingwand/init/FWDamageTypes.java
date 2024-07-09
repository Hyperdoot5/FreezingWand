package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import hyperdoot5.freezingwand.util.EntityExcludedDamageSource;

public class FWDamageTypes {

	public static final ResourceKey<DamageType> FROZEN = create("frozen"); // freeze


	public static ResourceKey<DamageType> create(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, FreezingWandMod.prefix(name));
	}

	public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> type, EntityType<?>... toIgnore) {
		return getEntityDamageSource(level, type, null, toIgnore);
	}

	public static DamageSource getEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker, EntityType<?>... toIgnore) {
		return getIndirectEntityDamageSource(level, type, attacker, attacker, toIgnore);
	}

	public static DamageSource getIndirectEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker, @Nullable Entity indirectAttacker, EntityType<?>... toIgnore) {
		return toIgnore.length > 0 ? new EntityExcludedDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), toIgnore) : new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), attacker, indirectAttacker);
	}

	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(FROZEN, new DamageType("freezingwand.frozen", 0.1F, DamageEffects.FREEZING));
	}
}
