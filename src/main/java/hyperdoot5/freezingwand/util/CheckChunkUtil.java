package hyperdoot5.freezingwand.util;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.slf4j.Logger;

import java.util.HashMap;

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
public class CheckChunkUtil {
    private static final Logger DEBUG = LogUtils.getLogger();

    //    public CheckWorldChunk(ClientTickEvent.Post event){
//        Minecraft mc = Minecraft.getInstance();
//        Player player = mc.player;
//        Level level = mc.level;
//
//        if (mc.level != null && player != null) {
//            HashSet<ChunkPos> nearbyChunks = new HashSet<>();
//            for (int x = -16; x <= 16 ; x += 16 ) {
//                DEBUG.info("Checking x: " + x);
//                for (int z = -16; z <= 16 ; z += 16) {
//                    DEBUG.info("Checking z: " + z);
//                    nearbyChunks.add(new ChunkPos((int) player.getX() + x, (int) player.getZ() + z));
//                    DEBUG.info("Added Chunk: " + nearbyChunks.size());
//                }
//            }
//            for (ChunkPos pos : nearbyChunks){
//                if (level.getChunk(pos.x, pos.z, ChunkStatus.FULL, false) != null){
//                    List<BlockState> savedBlocksInChunk = (level.getChunk(pos.x, pos.z).getBlockState());
//                }
//            }
//        }
//    }
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
        Level level = mc.level;
        //add a way to get user input for number of chunks between 1-5 in one direction from player position
        int NumberOfChunks = 1;
        int NumberOfBlocksDefault = 16;
        int NumberOfBlocks;
        if (level != null && !mc.isPaused() && player != null) {
            DEBUG.info("0");
            Position playerPos = player.position();
            Array2DHashSet<BlockState> blocksToSave = new Array2DHashSet<>();
            blocksToSave.add(Blocks.ICE.defaultBlockState());
            blocksToSave.add(Blocks.PACKED_ICE.defaultBlockState());
            blocksToSave.add(Blocks.BLUE_ICE.defaultBlockState());
            HashMap<BlockPos, BlockState> nearBlocks = new HashMap<>();

            switch (NumberOfChunks) {
                case 2 -> NumberOfBlocks = NumberOfBlocksDefault + 16;
                case 3 -> NumberOfBlocks = NumberOfBlocksDefault + 32;
                case 4 -> NumberOfBlocks = NumberOfBlocksDefault + 48;
                case 5 -> NumberOfBlocks = NumberOfBlocksDefault + 64;
                default -> NumberOfBlocks = NumberOfBlocksDefault;
            }
            for (int x = (int) (playerPos.x() - NumberOfBlocks); x <= (playerPos.x() + NumberOfBlocks); x++) {
                for (int z = (int) (playerPos.z() - NumberOfBlocks); z <= (playerPos.z() + NumberOfBlocks); z++) {
                    for (int y = (int) (playerPos.y() + 16); y >= (playerPos.y() - 16); y--) {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        BlockState blockState = level.getBlockState(blockPos);
                        if (blocksToSave.contains(blockState)) {
                            nearBlocks.put(blockPos, blockState);
                            DEBUG.info("Added Block: " + blockState);
                        }
//                        DEBUG.info("Y");
                    }
//                    DEBUG.info("Z");
                }
//                DEBUG.info("X");
            }
            if (!nearBlocks.isEmpty()) {
//                    String message = "There is a chill in the air..";
//                    player.sendSystemMessage(Component.translatable(message));
                DEBUG.info("1");
            } else {
                DEBUG.info("2");
//                    String message = "The air feels warmer..";
//                    player.sendSystemMessage(Component.translatable(message));
            }
        }
    }

//    public static class changeWandAnim {
//        public changeWandAnim defaultWandAnim() {
//            Minecraft mc = Minecraft.getInstance();
//            Player player = mc.player;
//            Level level = mc.level;
//
//            return wandAnim;
//        }
//    }
}
