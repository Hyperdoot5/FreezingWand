package hyperdoot5.freezingwand.item;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Tool;
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

        // check if can place
        if ((blockClicked == ice_block)
                || (blockClicked == packed_ice)
                || (blockClicked == snow_block)
                || (cardinalFluid == water_source)
                || (cardinalFluid == water_flowing)) {

            canPlace = true;
//            DEBUG.info("Click: "+ context.getClickLocation());
//            DEBUG.info("POV: "+ getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY));
//            DEBUG.info("WithPos: "+ getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY).withPosition(getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY).getBlockPos()));
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
            if ((cardinalFluid != water_source) || (cardinalFluid != water_flowing)) {
                context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
        }
        return canPlace ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
/*
            //add a way to detect blocks that are two away from clicked pos
            //create checkRelativePlayerChunkOnTick to check if near ice for animation
            //add ability to harvest ice blocks with wand


// Copy from Item.java, might need to refrence for future ideas
    @Override
    public boolean mineBlock(ItemStack p_41416_, Level p_41417_, BlockState p_41418_, BlockPos p_41419_, LivingEntity p_41420_) {
        Tool tool = p_41416_.get(DataComponents.TOOL);
        if (tool == null) {
            return false;
        } else {
            if (!p_41417_.isClientSide && p_41418_.getDestroySpeed(p_41417_, p_41419_) != 0.0F && tool.damagePerBlock() > 0) {
                p_41416_.hurtAndBreak(tool.damagePerBlock(), p_41420_, EquipmentSlot.MAINHAND);
            }

            return true;
        }
    }
    @Override
    public boolean isCorrectToolForDrops(ItemStack p_336002_, BlockState p_41450_) {
        Tool tool = p_336002_.get(DataComponents.TOOL);
        return tool != null && tool.isCorrectForDrops(p_41450_);
    }
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
        tooltip.add(Component.translatable("item.freezingwand.desc").withStyle(ChatFormatting.GRAY));
    }
}

