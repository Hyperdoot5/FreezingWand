package hyperdoot5.freezingwand.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import hyperdoot5.freezingwand.FreezingWandMod;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends IntrinsicHolderTagsProvider<Block> {
	public static final TagKey<Block> ICE_BOMB_REPLACEABLES = create("ice_bomb_replaceables");
	public static final TagKey<Block> WAND_BLOCKS = create("wand_blocks");
	// use commented out tag if creating a new tier
//    public static final TagKey<Block> INCORRECT_FOR_ICE_TOOL = create("incorrect_for_ice_tool");

	public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
		super(output, Registries.BLOCK, future, block -> block.builtInRegistryHolder().key(), FreezingWandMod.MODID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(ICE_BOMB_REPLACEABLES)
			.add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)
			.addTag(BlockTags.FLOWERS);

		this.tag(WAND_BLOCKS).add(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE);
//        this.tag(INCORRECT_FOR_ICE_TOOL).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
		;
	}

	public static TagKey<Block> create(String tagName) {
		return BlockTags.create(FreezingWandMod.prefix(tagName));
	}
}
