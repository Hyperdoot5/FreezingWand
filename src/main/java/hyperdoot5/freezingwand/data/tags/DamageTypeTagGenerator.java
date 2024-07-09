package hyperdoot5.freezingwand.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.init.FWDamageTypes;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGenerator extends TagsProvider<DamageType> {
	public DamageTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
		super(output, Registries.DAMAGE_TYPE, future, FreezingWandMod.MODID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(FWDamageTypes.FROZEN, Tags.DamageTypes.IS_MAGIC, DamageTypeTags.IS_FREEZING);

	}

	@SafeVarargs
	private void tag(ResourceKey<DamageType> type, TagKey<DamageType>... tags) {
		for (TagKey<DamageType> key : tags) {
			tag(key).add(type);
		}
	}

	private static TagKey<DamageType> create(String name) {
		return TagKey.create(Registries.DAMAGE_TYPE, FreezingWandMod.prefix(name));
	}
}
