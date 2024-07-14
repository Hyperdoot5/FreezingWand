package hyperdoot5.freezingwand.client;

import hyperdoot5.freezingwand.init.FWDataComponents;
import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

import java.util.Objects;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

////Events that are only on client side, not mod bus
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FWClientEvents {

        @SubscribeEvent
        public static void modelBake(ModelEvent.ModifyBakingResult event){
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.BASIC, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ATTUNEMENT) != null && Objects.equals(stack.get(FWDataComponents.WAND_ATTUNEMENT), "basic_attunement") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.BASIC_ANIMATION, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ANIMATION) != null && Objects.equals(stack.get(FWDataComponents.WAND_ANIMATION), "basic_animation") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.ICE, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ATTUNEMENT) != null && Objects.equals(stack.get(FWDataComponents.WAND_ATTUNEMENT), "ice_attunement") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.ICE_ANIMATION, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ANIMATION) != null && Objects.equals(stack.get(FWDataComponents.WAND_ANIMATION), "ice_animation") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.PACKED_ICE, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ATTUNEMENT) != null && Objects.equals(stack.get(FWDataComponents.WAND_ATTUNEMENT), "packed_ice_attunement") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.PACKED_ICE_ANIMATION, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ANIMATION) != null && Objects.equals(stack.get(FWDataComponents.WAND_ANIMATION), "packed_ice_animation") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.BLUE_ICE, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ATTUNEMENT) != null && Objects.equals(stack.get(FWDataComponents.WAND_ATTUNEMENT), "blue_ice_attunement") ? 1 : 0);
			ItemProperties.register(FWItems.FREEZING_WAND.get(), FreezingWandItem.BLUE_ICE_ANIMATION, (stack, level, entity, idk) ->
				stack.get(FWDataComponents.WAND_ANIMATION) != null && Objects.equals(stack.get(FWDataComponents.WAND_ANIMATION), "blue_ice_animation") ? 1 : 0);
		}
}