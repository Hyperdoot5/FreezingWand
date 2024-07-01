package hyperdoot5.freezingwand.client;

import com.mojang.logging.LogUtils;
import hyperdoot5.freezingwand.init.FWItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

////Events that are only on client side
//@EventBusSubscriber(modid = MODID)
//public class FWClientEvents {
//    //debug
//    private static final Logger DEBUG = LogUtils.getLogger();
//}
    //    public void onPlayerShiftLeftClick(Player player, InteractionHand hand, BlockPos pos, @Nullable Direction face){
//        Level level = player.level();
//        ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
//        BlockState blockAtPos = level.getBlockState(pos);
//        if (itemInHand == FWItems.FREEZING_WAND.toStack()){
//            if (player.isShiftKeyDown() && !level.isClientSide()){
//                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//                player.spawnAtLocation(Items.ICE.getDefaultInstance());
//            }
//        }
//    }
//}

//    //Events that must be on modEventBus
//    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ModBusEvents {
//
//    }

//    public static void breakBlock(BlockEvent.BreakEvent event) {
//        Player player = event.getPlayer();
//        ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
//        BlockPos blockPos = event.getPos();
//        BlockState blockState = event.getState();
//        BlockState air = Blocks.AIR.defaultBlockState();
//
//        if (itemInHand == FWItems.FREEZING_WAND.toStack()) {
//            DEBUG.info("a");
//
//            if (!(event.getLevel() instanceof ServerLevel level) || level.isClientSide()) return;
//
//            event.getLevel().setBlock(blockPos, air, 3);
//            player.spawnAtLocation(Items.ICE.getDefaultInstance());
//            DEBUG.info("sports");
//        }
//    }
//}
