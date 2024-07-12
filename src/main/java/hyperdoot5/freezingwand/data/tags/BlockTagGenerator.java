package hyperdoot5.freezingwand.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import hyperdoot5.freezingwand.FreezingWandMod;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends IntrinsicHolderTagsProvider<Block> {
	public static final TagKey<Block> ICE_BOMB_REPLACEABLES = create("ice_bomb_replaceables");

	public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
		super(output, Registries.BLOCK, future, block -> block.builtInRegistryHolder().key(), FreezingWandMod.MODID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(ICE_BOMB_REPLACEABLES)
			.add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)
			.addTag(BlockTags.FLOWERS);
	}

	public static TagKey<Block> create(String tagName) {
		return BlockTags.create(FreezingWandMod.prefix(tagName));
	}
}
