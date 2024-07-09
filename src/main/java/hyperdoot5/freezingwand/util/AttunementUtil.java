package hyperdoot5.freezingwand.util;

import hyperdoot5.freezingwand.init.FWDataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;

//@EventBusSubscriber(modid = MODID , value = Dist.CLIENT)
public class AttunementUtil {
	/*
	 * Attunement Definitions
	 * 1 = ice
	 * 2 = packed ice
	 * 3 = blue ice
	 * */


	// KNOWN ISSUE: when restarting minecraft, attunement resets to 1, but texture stays as previously selected attunement
	// issue resolves upon changing attunement due to design
	// add a way to save attunement position or reset texture on player login

	//NEED TO FIX SERVER INTERACTION W/ ATTUNEMENT UTIL
	//instances of attunementutil in serverbound cases results in null interaction due to util being dist.Client
	//however if util isnot dist.Client, mod will fail to load on server
	private static int attunement;

	private AttunementUtil() {
	}

	public static void cycleAttunement(ItemStack stack) {
		attunement++;
		if (attunement == 4) attunement = 0;
		setComponent(stack);
	}

	public static int getAttunement() {
//		DEBUG.info("Return: " + attunement);
		return attunement;
	}

	public static void setAttunement(int newAttunement, ItemStack stack) {
		attunement = newAttunement;
		setComponent(stack);
//		DEBUG.info("Attunement: " +attunement);
	}

	// doesnt matter if null, added null description translation
	public static String getComponent(ItemStack stack) {
		return stack.get(FWDataComponents.WAND_ATTUNEMENT);
	}

	public static void setComponent(ItemStack stack) {
//		DEBUG.info("Was: " + getComponent(stack));
		stack.remove(FWDataComponents.WAND_ATTUNEMENT);
		switch (attunement) {
			case 0 -> stack.set(FWDataComponents.WAND_ATTUNEMENT, "basic_attunement");
			case 1 -> stack.set(FWDataComponents.WAND_ATTUNEMENT, "ice_attunement");
			case 2 -> stack.set(FWDataComponents.WAND_ATTUNEMENT, "packed_ice_attunement");
			case 3 -> stack.set(FWDataComponents.WAND_ATTUNEMENT, "blue_ice_attunement");

		}
//		DEBUG.info("Set: " + getComponent(stack));
	}

	public static boolean isCorrectComponent(String component) {
		String attune = "";
		switch (attunement) {
			case 0 -> attune = "basic_attunement";
			case 1 -> attune = "ice_attunement";
			case 2 -> attune = "packed_ice_attunement";
			case 3 -> attune = "blue_ice_attunement";
		}
		return component.equals(attune);
	}
}

