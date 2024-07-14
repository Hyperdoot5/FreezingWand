package hyperdoot5.freezingwand.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.entity.*;
import hyperdoot5.freezingwand.entity.projectile.*;

public class FWEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, FreezingWandMod.MODID);

	public static final DeferredHolder<EntityType<?>, EntityType<IceBomb>> THROWN_ICE = buildNoEgg(FWEntityNames.THROWN_ICE, makeCastedBuilder(IceBomb.class, IceBomb::new, 1.0F, 1.0F, 80, 2), true);

	private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> buildNoEgg(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof) {
		if (fireproof) builder.fireImmune();
		return ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
	}

	private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(Class<E> cast, EntityType.EntityFactory<E> factory, float width, float height, int range, int interval) {
		return makeBuilder(factory, MobCategory.MISC, width, height, range, interval, 0.0F);
	}

	private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval, float ridingOffset) {
		return EntityType.Builder.of(factory, classification)
			.sized(width, height)
			.setTrackingRange(range)
			.setUpdateInterval(interval)
			.setShouldReceiveVelocityUpdates(true)
			.ridingOffset(ridingOffset);
	}
}
