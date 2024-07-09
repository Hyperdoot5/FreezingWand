package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.FreezingWandMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;
import hyperdoot5.freezingwand.enchantment.*;

public class FWEnchantments {
	public static final ResourceKey<Enchantment> FROZONE = registerKey("frozone");

	private static ResourceKey<Enchantment> registerKey(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, FreezingWandMod.prefix(name));
	}

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<DamageType> damageTypes = context.lookup(Registries.DAMAGE_TYPE);
		HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		register(context, FROZONE, new Enchantment.Builder(Enchantment.definition(
			items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
			items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
			1,
			3,
			Enchantment.dynamicCost(5, 9),
			Enchantment.dynamicCost(20, 9),
			8,
			EquipmentSlotGroup.ARMOR)
		).exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.THORNS)))
			.withEffect(EnchantmentEffectComponents.POST_ATTACK,
				EnchantmentTarget.VICTIM,
				EnchantmentTarget.ATTACKER,
				AllOf.entityEffects(
					new ApplyFrostedEffect(LevelBasedValue.constant(200), LevelBasedValue.perLevel(0.0F, 1.0F)),
					new DamageItem(LevelBasedValue.constant(2.0F))),
				LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.15F)))));
	}

	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.location()));
	}
}
