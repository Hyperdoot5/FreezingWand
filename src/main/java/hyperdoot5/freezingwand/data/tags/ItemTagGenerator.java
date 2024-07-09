//package hyperdoot5.freezingwand.data.tags;
//
//import hyperdoot5.freezingwand.FreezingWandMod;
//import hyperdoot5.freezingwand.init.FWItems;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.PackOutput;
//import net.minecraft.data.tags.ItemTagsProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.neoforged.neoforge.common.Tags;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//
//import javax.annotation.Nullable;
//import java.util.Objects;
//import java.util.concurrent.CompletableFuture;
//
//public class ItemTagGenerator extends ItemTagsProvider {
////use commented  out tags if adding new tool tier
////    public static final TagKey<Item> REPAIRS_WAND = create("repairs_wand");
//    public static final TagKey<Item> WAND = create("wand");
//
//    public ItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
//        super(output, future, provider, FreezingWandMod.MODID, helper);
//    }
//
//    @Override
//    protected void addTags(HolderLookup.Provider provider) {
//        this.tag(WAND).add(FWItems.FREEZING_WAND.get());
////        this.tag(REPAIRS_WAND).add(Blocks.ICE.asItem(), Blocks.PACKED_ICE.asItem(), Blocks.BLUE_ICE.asItem());
//    }
//
//    public static TagKey<Item> create(String tagName) {
//        return ItemTags.create(FreezingWandMod.prefix(tagName));
//    }
//}
