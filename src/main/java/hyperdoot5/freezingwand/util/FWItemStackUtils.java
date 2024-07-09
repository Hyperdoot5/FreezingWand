package hyperdoot5.freezingwand.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class FWItemStackUtils {
	public static boolean hasInfoTag(ItemStack stack, String key) {
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		return customData != null && customData.contains(key);
	}

	public static void addInfoTag(ItemStack stack, String key) {
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		CompoundTag nbt = customData == null ? new CompoundTag() : customData.copyTag();
		nbt.putBoolean(key, true);
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
	}

	public static void clearInfoTag(ItemStack stack, String key) {
		CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
		if (customData != null) {
			CompoundTag nbt = customData.copyTag();
			nbt.remove(key);
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
		}
	}
}
