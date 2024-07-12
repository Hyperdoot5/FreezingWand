package hyperdoot5.freezingwand.data;

import hyperdoot5.freezingwand.data.helpers.FWLangProvider;
import hyperdoot5.freezingwand.init.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class LangGenerator extends FWLangProvider {
    public static final Map<String, String> SUBTITLE_GENERATOR = new HashMap<>();

    public LangGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void addTranslations() {

        this.add("itemGroup.freezingwand", "Freezing Wand: Wand");

        this.addAdvancement("root", "Freezing Wand", "Start gathering materials to craft the Freezing Wand by obtaining Ice");
        this.addAdvancement("progress_packed_ice", "Chills", "Obtain Packed Ice");
        this.addAdvancement("progress_blue_ice", "A Dense Cold", "Gather the final material for the wand, Obtain Blue Ice");
        this.addAdvancement("freezing_wand", "Ice Ice Baby", "Craft the Freezing Wand");

        this.addItem(FWItems.FREEZING_WAND, "Freezing Wand");
		this.add("item.freezingwand.desc.basic_attunement", "Attunement: Ranged");
		this.add("item.freezingwand.desc.ice_attunement", "Attunement: Ice");
		this.add("item.freezingwand.desc.packed_ice_attunement", "Attunement: Packed Ice");
		this.add("item.freezingwand.desc.blue_ice_attunement", "Attunement: Blue Ice");
		this.add("item.freezingwand.resonate", "Resonating");

        SUBTITLE_GENERATOR.forEach(this::add);

		this.addStat("ice_collected", "Ice Blocks Collected");
        this.addStat("packed_ice_collected", "Packed Ice Blocks Collected");
        this.addStat("blue_ice_collected", "Blue Ice Blocks Collected");

        this.add("key.categories.freezingwand.freezingwand", "Freezing Wand");
        this.add("key.freezingwand.attunement", "Cycle Wand Attunement");
		this.add("key.freezingwand.basic", "Ranged Attunement");
		this.add("key.freezingwand.ice", "Ice Attunement");
		this.add("key.freezingwand.packed_ice", "Packed Ice Attunement");
		this.add("key.freezingwand.blue_ice", "Blue Ice Attunement");

		this.add("effect.freezingwand.frosted", "Frosted");
    }
}
