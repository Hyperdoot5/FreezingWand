package hyperdoot5.freezingwand.data;

import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;
import static hyperdoot5.freezingwand.FreezingWandMod.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile basic = wand("freezing_wand_basic", prefix("item/freezing_wand/basic"));
        ModelFile ice = wand("freezing_wand_ice", prefix("item/freezing_wand/ice"));
        ModelFile packed_ice = wand("freezing_wand_packed_ice", prefix("item/freezing_wand/packed_ice"));
        ModelFile blue_ice = wand("freezing_wand_blue_ice", prefix("item/freezing_wand/blue_ice"));
        wand(FWItems.FREEZING_WAND.getId().getPath(), prefix("item/freezing_wand/basic"))
			.override().predicate(FreezingWandMod.prefix("basic_attunement"), 1).model(basic).end()
			.override().predicate(FreezingWandMod.prefix("ice_attunement"), 1).model(ice).end()
			.override().predicate(FreezingWandMod.prefix("packed_ice_attunement"), 1).model(packed_ice).end()
			.override().predicate(FreezingWandMod.prefix("blue_ice_attunement"), 1).model(blue_ice).end();
    }
    private ItemModelBuilder wand(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
}
