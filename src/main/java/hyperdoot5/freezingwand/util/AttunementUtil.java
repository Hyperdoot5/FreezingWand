package hyperdoot5.freezingwand.util;

import hyperdoot5.freezingwand.init.FWDataComponents;
import hyperdoot5.freezingwand.network.AttunementChangePacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

import static hyperdoot5.freezingwand.item.FreezingWandItem.basicComponent;
import static hyperdoot5.freezingwand.item.FreezingWandItem.iceComponent;
import static hyperdoot5.freezingwand.item.FreezingWandItem.packedIceComponent;
import static hyperdoot5.freezingwand.item.FreezingWandItem.blueIceComponent;

//@EventBusSubscriber(modid = MODID , value = Dist.CLIENT)
public class AttunementUtil {
	/*
	 * Attunement Definitions
	 * 0 = ranged
	 * 1 = ice
	 * 2 = packed ice
	 * 3 = blue ice
	 * */

	private AttunementUtil() {
	}

	public static void cycleAttunement(ItemStack stack) {
		String oldComponent = getComponent(stack);
		String newComponent = "";
		switch (oldComponent) {
			case basicComponent -> {
				newComponent = iceComponent;
				setComponent(iceComponent, stack);
			}
			case iceComponent -> {
				newComponent = packedIceComponent;
				setComponent(packedIceComponent, stack);
			}
			case packedIceComponent -> {
				newComponent = blueIceComponent;
				setComponent(blueIceComponent, stack);
			}
			case blueIceComponent -> {
				newComponent = basicComponent;
				setComponent(basicComponent, stack);
			}
		}
		PacketDistributor.sendToServer(new AttunementChangePacket(newComponent));
	}

	@Nullable
	public static String getComponent(ItemStack stack) {
		return stack.get(FWDataComponents.WAND_ATTUNEMENT);
	}

	public static void setComponent(String component, ItemStack stack) {
		switch (component) {
			case basicComponent -> stack.set(FWDataComponents.WAND_ATTUNEMENT, basicComponent);
			case iceComponent -> stack.set(FWDataComponents.WAND_ATTUNEMENT, iceComponent);
			case packedIceComponent -> stack.set(FWDataComponents.WAND_ATTUNEMENT, packedIceComponent);
			case blueIceComponent -> stack.set(FWDataComponents.WAND_ATTUNEMENT, blueIceComponent);

		}
	}
}

