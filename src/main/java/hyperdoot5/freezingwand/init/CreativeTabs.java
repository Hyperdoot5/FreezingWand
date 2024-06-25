package hyperdoot5.freezingwand.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static hyperdoot5.freezingwand.FreezingWand.MODID;
import static hyperdoot5.freezingwand.init.ModItems.FREEZING_WAND;

public class CreativeTabs {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "freezingwand" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    // Creates a creative tab with the id "freezingwand: freezing_wand" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FREEZING_WAND_TAB = CREATIVE_MODE_TABS.register("freezingwand", () -> CreativeModeTab.builder()
            .title(Component.translatable("Hocus Pocus")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> FREEZING_WAND.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(FREEZING_WAND.get()); // Add the item to the tab.
            }).build());
}
