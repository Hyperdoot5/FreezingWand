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
//        this.add("effect.freezingwand.frosted", "Frosted");

        this.addAdvancement("root", "Freezing Wand", "Start gathering materials to craft the Freezing Wand by obtaining Ice");
        this.addAdvancement("progress_packed_ice", "Chills", "Obtain Packed Ice");
        this.addAdvancement("progress_blue_ice", "A Dense Cold", "Gather the final material for the wand, Obtain Blue Ice");
        this.addAdvancement("freezing_wand", "Ice Ice Baby", "Craft the Freezing Wand");

//        this.addMessage("advancement_hidden", "<Hidden Advancement>");
//        this.addMessage("advancement_required", "Advancement Required:");
//        this.addMessage("ore_meter_separator", "-");
//        this.addMessage("ore_meter_ratio", "(%s%%)");
//        this.addMessage("ore_meter_range", "Radius: %s, Origin: [%s, %s]");

        this.addItem(FWItems.FREEZING_WAND, "Freezing Wand");
		this.add("item.freezingwand.desc.basic_attunement", "Attunement: Ranged");
		this.add("item.freezingwand.desc.ice_attunement", "Attunement: Ice");
		this.add("item.freezingwand.desc.packed_ice_attunement", "Attunement: Packed Ice");
		this.add("item.freezingwand.desc.blue_ice_attunement", "Attunement: Blue Ice");
		this.add("item.freezingwand.desc.null", "Unattuned Wand");

//        this.add("item.freezingwand.skull_candle.desc", "Has: %s %s Candle");
//        this.add("item.freezingwand.skull_candle.desc.multiple", "Has: %s %s Candles");


//        this.addEntityAndEgg(TFEntities.ADHERENT, "Adherent");
        SUBTITLE_GENERATOR.forEach(this::add);

//        this.addDeathMessage("ghastTear", "%1$s was scalded by fiery tears");
//        this.addDeathMessage("axing.item", "%1$s was chopped up by %2$s using %3$s");

        this.addStat("ice_colleted", "Ice Blocks Collected");
        this.addStat("packed_ice_collected", "Packed Ice Blocks Collected");
        this.addStat("blue_ice_collected", "Blue Ice Blocks Collected");


//        this.add("config.jade.plugin_freezingwand.quest_ram_wool", "Questing Ram Wool");
//        this.add("config.jade.plugin_freezingwand.chiseled_bookshelf_spawner", "Chiseled Canopy Bookshelf Spawns");

//        this.add("freezingwand.book.author", "a forgotten explorer");

//        this.addBookAndContents("lichtower", "Notes on a Pointy Tower",
//                "§8[An explorer's notebook, gnawed on by monsters]§0\n\nI have begun examining the strange aura surrounding this tower. The bricks of the tower are protected by a curse, stronger than any I've seen before. The magic from the curse is boiling off into the",
//                "surrounding area.\n\nIn my homeland I would have many options for dealing with this magic, but here my supplies are limited. I shall have to research...",
//                "§8[[Many entries later]]§0\n\nA breakthrough! In my journeys I sighted a huge snake-like monster in a decorated courtyard. Nearby, I picked up a worn down, discarded green scale.\n\nThe magic in the scale seems to have the curse-breaking",
//                "properties I need, but the magic is too dim. I may need to acquire a fresher specimen, directly from the creature.");

//        this.addScreenMessage("optifine.title", "WARNING: OPTIFINE DETECTED");
//        this.addScreenMessage("optifine.message", "Before proceeding, please note that Optifine is known to cause crashes, multipart entity visual bugs and many other issues.\n\nBefore reporting a bug, please remove Optifine first and check again to see if the bug is still present.\n\nOptifine-related issues are not solvable on Twilight Forest's end!\n\nThis screen may be disabled in the Client Config.");
//        this.addScreenMessage("optifine.suggestions", "Here's a selection of mods that we recommend using instead.");
//        this.addScreenMessage("moonworm_queen_jei", "Moonworm Queen Repairing");
//        this.add("item.freezingwand.moonworm_queen.jei_info_message", "Torchberries restore 64 durability each");

        this.add("key.categories.freezingwand.freezingwand", "Freezing Wand");
        this.add("key.freezingwand.attunement", "Cycle Wand Attunement");
		this.add("key.freezingwand.basic", "Ranged Attunement");
		this.add("key.freezingwand.ice", "Ice Attunement");
		this.add("key.freezingwand.packed_ice", "Packed Ice Attunement");
		this.add("key.freezingwand.blue_ice", "Blue Ice Attunement");
//        this.createTip("anvil_squashing", "Bugs can be squashed by Anvils.");


//        this.translateTag(ItemTagGenerator.CARMINITE_GEMS, "Carminite Gems");

//        this.translateTag(FluidTagGenerator.FIRE_JET_FUEL, "Fire Jet Fuel");
    }
}
