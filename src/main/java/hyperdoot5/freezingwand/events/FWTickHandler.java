package hyperdoot5.freezingwand.events;

import hyperdoot5.freezingwand.FreezingWandMod;
import hyperdoot5.freezingwand.item.FreezingWandItem;
import hyperdoot5.freezingwand.util.AttunementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

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
        int Nticks = 20;
        // only run every N ticks if player is holding the Freezing Wand
        if (player != null && player.tickCount % Nticks == 0 && (player.getMainHandItem().getItem() instanceof FreezingWandItem)) {
            Level level = mc.level;
            //add a way to get user input for number of chunks between 1-5 in one direction from player position
            int NumberOfChunks = 1;
            int NumberOfBlocksDefault = 8; // half a chunk because player as at the center
            int NumberOfBlocks;
            if (level != null && !mc.isPaused()) {
//                DEBUG.info("0");
                Position playerPos = player.position();
                List<BlockState> blocksToSave = new ArrayList<>();
                blocksToSave.add(Blocks.ICE.defaultBlockState());
                blocksToSave.add(Blocks.PACKED_ICE.defaultBlockState());
                blocksToSave.add(Blocks.BLUE_ICE.defaultBlockState());
                HashMap<BlockPos, BlockState> nearBlocks = new HashMap<>();

                switch (NumberOfChunks) {
                    case 2 -> NumberOfBlocks = NumberOfBlocksDefault * 3; //8 + 16
                    case 3 -> NumberOfBlocks = NumberOfBlocksDefault * 5; //8 + 32
                    case 4 -> NumberOfBlocks = NumberOfBlocksDefault * 7;//etc...
                    case 5 -> NumberOfBlocks = NumberOfBlocksDefault * 9;
                    default -> NumberOfBlocks = NumberOfBlocksDefault;
                }
                for (int x = (int) (playerPos.x() - NumberOfBlocks); x <= (playerPos.x() + NumberOfBlocks); x++) {
                    for (int z = (int) (playerPos.z() - NumberOfBlocks); z <= (playerPos.z() + NumberOfBlocks); z++) {
                        for (int y = (int) (playerPos.y() + NumberOfBlocksDefault); y >= (playerPos.y() - NumberOfBlocksDefault); y--) {
                            BlockPos blockPos = new BlockPos(x, y, z);
                            BlockState blockChecked = level.getBlockState(blockPos);
                            if (blocksToSave.contains(blockChecked)) {
                                nearBlocks.put(blockPos, blockChecked);
//                                DEBUG.info("Added Block: " + nearBlocks.get(blockPos));
//                                DEBUG.info("With Position: " + blockPos);
                            }
                        }
                    }
//                DEBUG.info("Checked Chunks");
                }
                if (!nearBlocks.isEmpty()) {
//                        String message = "There is a chill in the air..";
//                        player.sendSystemMessage(Component.translatable(message));
//                        FWItems.FREEZING_WAND.toStack().set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
/*
grab data componenet from wand
create var for data component of closest block
if the data component is NOT the same as the data component related to the closest block, then set data component to closest block type
Update paked, i think
for (String key : map.keyset())
*/

//                    DEBUG.info("1");
                } else {
//                    DEBUG.info("2");
//                        String message = "The air feels warmer..";
//                        player.sendSystemMessage(Component.translatable(message));
                }
            }
        }
    }

}

/*public static Iterable<BlockPos> getAllAround(BlockPos center, int range) {
		return BlockPos.betweenClosed(center.offset(-range, -range, -range), center.offset(range, range, range));
	}*/