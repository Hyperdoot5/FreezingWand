package hyperdoot5.freezingwand.events;

import hyperdoot5.freezingwand.init.FWDataComponents;
import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.*;

import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;
import static hyperdoot5.freezingwand.FreezingWandMod.MODID;
import static hyperdoot5.freezingwand.item.FreezingWandItem.*;
import static hyperdoot5.freezingwand.util.AttunementUtil.getComponent;
import static hyperdoot5.freezingwand.util.AttunementUtil.setComponent;

/*PSEUDO CODE
 * Every second get player position and run
 *make as customizable as possible, if we can replace param with var do so to enable easy customization
 * make both versions and allow user to decide how to check for blocks w/ customizations
 *
 * CheckWorldChunk Versions
 * get worldChunkPos player is in _> store in chunk array
 * get worldChunkPos around player position of detectionRange size (default 3x3x3)-> store in chunk array
 * check each chunk for ice, packed ice, and blue ice (iterative for loop) -> store block position in savedBlock array w/ blockType -> store both data in blockHashMap?
 * !savedBlock.isEmpty() ? changeWandAnim : return;
 *
 * CheckRelativePlayerChunk Version
 * draw a chunk around the player position (iterative for loop)
 * {
 * check each block during draw -> if blockChecked == ice, packed ice, or blue ice -> store in nearBlockList & set boolean nearBlock true
 * if nearBlock return changeWandAnim.blockChecked, else continue checking blocks
 * }
 * if nearBlockList.isEmpty() -> changeWandAnim.DefaultState
 *
 * changeWandAnim
 * check nearBlockList and set the wand animation based on the highest number of block type found*/
@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public abstract class FWTickHandler {

//	@SubscribeEvent
//	public static void CheckForWand(ClientTickEvent.Post event){
//		Minecraft mc = Minecraft.getInstance();
//		Player player = mc.player;
//		if (player != null && player.tickCount % 20 == 0 && player.getMainHandItem().getItem() instanceof FreezingWandItem){
//			AttunementUtil.getComponent(player.getMainHandItem());
//
//		}
//	}

	@SubscribeEvent
	public static void CheckRelativePlayerChunk(ClientTickEvent.Post event) {
		/*   CheckRelativePlayerChunk Version
		 * draw a chunk around the player position (iterative for loop)
		 * {
		 * check each block during draw -> if blockChecked == ice, packed ice, or blue ice -> store in nearBlockList & set boolean nearBlock true
		 * if nearBlock return changeWandAnim.blockChecked, else continue checking blocks
		 * }
		 * if nearBlockList.isEmpty() -> changeWandAnim.DefaultState
		 */

		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		int Nticks = 10;
		// only run every N ticks if player is holding the Freezing Wand
		if (player != null && player.tickCount % Nticks == 0 && (player.getMainHandItem().getItem() instanceof FreezingWandItem)) {
			ItemStack stack = player.getMainHandItem();
			if (!Objects.equals(getComponent(stack), basicComponent)) {
				Level level = mc.level;
				//add a way to get user input for number of chunks between 1-5 in one direction from player position
				int NumberOfChunks = 1;
				int NumberOfBlocksDefault = 8; // half a chunk because player as at the center
				int NumberOfBlocks;
				if (level != null && !mc.isPaused()) {
					Position playerPos = player.position();
					BlockState blockToSave = null;
					String component = getComponent(stack);
					switch (component) {
						case iceComponent -> blockToSave = Blocks.ICE.defaultBlockState();
						case packedIceComponent -> blockToSave = Blocks.PACKED_ICE.defaultBlockState();
						case blueIceComponent -> blockToSave = Blocks.BLUE_ICE.defaultBlockState();
					}
//                HashMap<Double, BlockState> nearBlocks = new HashMap<>();
					boolean nearBlocks = false;

					switch (NumberOfChunks) {
						case 2 -> NumberOfBlocks = NumberOfBlocksDefault * 3; //8 + 16
						case 3 -> NumberOfBlocks = NumberOfBlocksDefault * 5; //8 + 32
						case 4 -> NumberOfBlocks = NumberOfBlocksDefault * 7;//etc...
						case 5 -> NumberOfBlocks = NumberOfBlocksDefault * 9;
						default -> NumberOfBlocks = NumberOfBlocksDefault;
					}
					for (int x = (int) (playerPos.x() - NumberOfBlocks); x <= (playerPos.x() + NumberOfBlocks); x++) {
						if (nearBlocks) {
							break;
						}
						for (int z = (int) (playerPos.z() - NumberOfBlocks); z <= (playerPos.z() + NumberOfBlocks); z++) {
							if (nearBlocks) {
								break;
							}
							for (int y = (int) (playerPos.y() + NumberOfBlocksDefault); y >= (playerPos.y() - NumberOfBlocksDefault); y--) {
								BlockPos blockPos = new BlockPos(x, y, z);
								BlockState blockChecked = level.getBlockState(blockPos);
								if (blockToSave == blockChecked) {
//                                nearBlocks.put(blockPos.distToCenterSqr(playerPos), blockChecked);
									nearBlocks = true;
									break;
								}
							}
						}
					}
					if (nearBlocks) {
						switch (component) {
							case iceComponent -> setComponent(iceAnimatedComponent, stack);
							case packedIceComponent -> setComponent(packedIceAnimatedComponent, stack);
							case blueIceComponent -> setComponent(blueIceAnimatedComponent, stack);
						}
					} else {
						if (stack.has(FWDataComponents.WAND_ANIMATION)) {
							stack.remove(FWDataComponents.WAND_ANIMATION);
						}
					}
				}
			} else if (stack.has(FWDataComponents.WAND_ANIMATION) && !Objects.equals(stack.get(FWDataComponents.WAND_ANIMATION), basicAnimatedComponent)) {
				stack.remove(FWDataComponents.WAND_ANIMATION);
			}
			if (Objects.equals(stack.get(FWDataComponents.WAND_ATTUNEMENT), basicComponent)) {
				if (player.isShiftKeyDown()) {
					stack.set(FWDataComponents.WAND_ANIMATION, basicAnimatedComponent);
				} else if (!player.isShiftKeyDown() && Objects.equals(stack.get(FWDataComponents.WAND_ANIMATION), basicAnimatedComponent)) {
					stack.remove(FWDataComponents.WAND_ANIMATION);
				}
			}
		}
	}

}

/*public static Iterable<BlockPos> getAllAround(BlockPos center, int range) {
		return BlockPos.betweenClosed(center.offset(-range, -range, -range), center.offset(range, range, range));
	}*/