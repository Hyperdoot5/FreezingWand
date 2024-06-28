package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

public class FWItems {
    // Create a Deferred Register to hold Items which will all be registered under the "freezingwandmod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    // Item registering
    public static final DeferredItem<Item> FREEZING_WAND = ITEMS.register(
            "freezing_wand", () -> new FreezingWandItem(new Item.Properties()
                    .durability(100)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
}