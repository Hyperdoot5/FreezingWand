package hyperdoot5.freezingwand.client;

import hyperdoot5.freezingwand.init.FWDataComponents;
import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

//Events that are only on client side, not mod bus
@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class FWClientEvents {

    //Events that must be on modEventBus
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void modelBake(ModelEvent.ModifyBakingResult event){
            ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.BASIC, (stack, level, entity, idk) ->
                    stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS) != null && stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS).equals("basic") ? 1 : 0);
            ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.ICE, (stack, level, entity, idk) ->
                    stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS) != null && stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS).equals("ice") ? 1 : 0);
            ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.PACKED_ICE, (stack, level, entity, idk) ->
                    stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS) != null && stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS).equals("packed_ice") ? 1 : 0);
            ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.BLUE_ICE, (stack, level, entity, idk) ->
                    stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS) != null && stack.get(FWDataComponents.FREEZING_WAND_ATTUNEMENTS).equals("blue_ice") ? 1 : 0);
        }
    }
}

/*
    event.enqueueWork(() -> { // ItemProperties#register is not threadsafe, so we need to call it on the main thread
        ItemProperties.register(
        // The item to apply the property to.
        FWItems.FREEZING_WAND.asItem(),
// The id of the property.
                    ResourceLocation.fromNamespaceAndPath(MODID, "custom_model_data"),
// A reference to a method that calculates the override value.
// Parameters are the used item stack, the level context, the player using the item,
// and a random seed you can use.
                    (stack, level, player, seed) -> {
        if (blockSelPos == 1){
        return 1F;
        } else if (blockSelPos == 2) {
        return 2F;
        } else if (blockSelPos == 3) {
        return 3F;
        }
        return 0F;
        }
        );
        });

 */