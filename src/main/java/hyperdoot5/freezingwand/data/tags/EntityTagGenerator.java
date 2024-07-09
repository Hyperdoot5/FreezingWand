package hyperdoot5.freezingwand.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.init.FWEntities;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
	public EntityTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
		super(output, provider, FreezingWandMod.MODID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(FWEntities.THROWN_ICE.get());
	}

	private static TagKey<EntityType<?>> create(ResourceLocation rl) {
		return TagKey.create(Registries.ENTITY_TYPE, rl);
	}
}
