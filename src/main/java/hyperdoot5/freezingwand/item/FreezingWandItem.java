package hyperdoot5.freezingwand.item;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class FreezingWandItem extends Item {

    public FreezingWandItem(Item.Properties properties) {
        super(properties);
    }
/*
     Uncomment for Debug
     private static final Logger DEBUG = LogUtils.getLogger();
*/
    //Item Functionality
    //When player right clicks, place ice block on the face of the block they clicked if that face is next to air
    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Variables for readability
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockState water_source = Blocks.WATER.defaultBlockState();
        FluidState water_flowing = Fluids.FLOWING_WATER.defaultFluidState();
        BlockState ice_block = Blocks.ICE.defaultBlockState();
        BlockState packed_ice = Blocks.PACKED_ICE.defaultBlockState();
        BlockState snow_block = Blocks.SNOW_BLOCK.defaultBlockState();
        BlockState snow = Blocks.SNOW.defaultBlockState();
        BlockPos position = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState cardinalBlock = level.getBlockState(position.relative(direction));
        FluidState cardinalFluid = level.getFluidState(position.relative(direction));

        //

        if ((!level.isClientSide()) && (player != null)
                && ((cardinalBlock == water_source)
                || (cardinalBlock == ice_block)
                || (cardinalFluid == water_flowing))
                || (cardinalBlock == packed_ice)
                || (cardinalBlock == snow)
                || (cardinalBlock == snow_block)) {

            switch(direction){
                case UP -> level.setBlock(position.relative(Direction.UP), ice_block, 3);
                case DOWN -> level.setBlock(position.relative(Direction.DOWN), ice_block, 3);
                case NORTH -> level.setBlock(position.relative(Direction.NORTH), ice_block, 3);
                case EAST -> level.setBlock(position.relative(Direction.EAST), ice_block, 3);
                case SOUTH -> level.setBlock(position.relative(Direction.SOUTH), ice_block, 3);
                case WEST -> level.setBlock(position.relative(Direction.WEST), ice_block, 3);
            }
            level.playSound(player, position,SoundEvents.STONE_PLACE, SoundSource.PLAYERS); //play block place sound on interact
            context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

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

