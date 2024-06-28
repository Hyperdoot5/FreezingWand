package hyperdoot5.freezingwand.item;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.slf4j.Logger;

import java.util.List;


public class FreezingWandItem extends Item {

    //Debug
    private static final Logger DEBUG = LogUtils.getLogger();

    public FreezingWandItem(Properties properties) {
        super(properties);
    }

    //Item Functionality
    //When player right clicks, place ice block on the face of the block they clicked if that face is next to air
    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Variables for readability
        Player player = context.getPlayer();
        Level level = context.getLevel();
        Fluid water_flowing = Fluids.FLOWING_WATER.getFlowing();
        Fluid water_source = Fluids.WATER.getSource();
        BlockState ice_block = Blocks.ICE.defaultBlockState();
        BlockState packed_ice = Blocks.PACKED_ICE.defaultBlockState();
        BlockState snow_block = Blocks.SNOW_BLOCK.defaultBlockState();
        BlockPos clickedPos = context.getClickedPos();
        Direction cardinalDirection = context.getClickedFace();
//        BlockState cardinalBlock = level.getBlockState(clickedPos.relative(cardinalDirection));
        BlockState blockClicked = level.getBlockState(clickedPos);
        Fluid cardinalFluid = level.getFluidState(clickedPos.relative(cardinalDirection)).getType();

        boolean canPlace = false;

        if((blockClicked == ice_block)
                || (blockClicked == packed_ice)
                || (blockClicked == snow_block)
                || (cardinalFluid == water_source)
                || (cardinalFluid == water_flowing)
        ) {
            canPlace = true;

            DEBUG.info("Click: "+ context.getClickLocation());
            DEBUG.info("POV: "+ getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY));
            DEBUG.info("WithPos: "+ getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY).withPosition(getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY).getBlockPos()));
            if (!level.isClientSide) {
                switch (cardinalDirection) {
                    case UP -> level.setBlock(clickedPos.relative(Direction.UP), ice_block, 3);
                    case DOWN -> level.setBlock(clickedPos.relative(Direction.DOWN), ice_block, 3);
                    case NORTH -> level.setBlock(clickedPos.relative(Direction.NORTH), ice_block, 3);
                    case EAST -> level.setBlock(clickedPos.relative(Direction.EAST), ice_block, 3);
                    case SOUTH -> level.setBlock(clickedPos.relative(Direction.SOUTH), ice_block, 3);
                    case WEST -> level.setBlock(clickedPos.relative(Direction.WEST), ice_block, 3);
                }
            }
            level.playSound(player, clickedPos, SoundEvents.STONE_PLACE, SoundSource.PLAYERS);
            // Only reduce durability if not building directly from water
            if((cardinalFluid != water_source) || (cardinalFluid != water_flowing)) {
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
        }
        return canPlace ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
/*
            //add a way to detect blocks that are two away from clicked pos
            //create checkRelativePlayerChunkOnTick to check if near ice for animation

*/

    // Item Properties
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    // custom tooltip
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, context, tooltip, flags);
        tooltip.add(Component.translatable("item.freezingwand.description").withStyle(ChatFormatting.GRAY));
    }
}

