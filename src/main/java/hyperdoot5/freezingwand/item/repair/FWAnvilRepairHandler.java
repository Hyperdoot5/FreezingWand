package hyperdoot5.freezingwand.item.repair;

import hyperdoot5.freezingwand.init.FWItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

@EventBusSubscriber(modid = MODID)
public class FWAnvilRepairHandler {
    //repair freezing wand in anvil using ice, packed ice, or blue ice
    @SubscribeEvent
    public static void updateAnvilIce(AnvilUpdateEvent event) {
        if (event.getLeft().is(FWItems.FREEZING_WAND) && event.getRight().is(Blocks.ICE.asItem())) {
            //replace wand for 5XpLVLs and one ice block
            event.setOutput(FWItems.FREEZING_WAND.toStack());
            event.setCost(5);
            event.setMaterialCost(1);
        }
    }
    @SubscribeEvent
    public static void updateAnvilPackedIce(AnvilUpdateEvent event) {
        if (event.getLeft().is(FWItems.FREEZING_WAND) && event.getRight().is(Blocks.PACKED_ICE.asItem())) {
            //replace wand for 3XpLVLs and one packed ice block
            event.setOutput(FWItems.FREEZING_WAND.toStack());
            event.setCost(3);
            event.setMaterialCost(1);
        }
    }
    @SubscribeEvent
    public static void updateAnvilBlueIce(AnvilUpdateEvent event) {
        if (event.getLeft().is(FWItems.FREEZING_WAND) && event.getRight().is(Blocks.BLUE_ICE.asItem())) {
            //replace wand for 1XpLVL and one blue ice block
            event.setOutput(FWItems.FREEZING_WAND.toStack());
            event.setCost(1);
            event.setMaterialCost(1);
        }
    }
}
