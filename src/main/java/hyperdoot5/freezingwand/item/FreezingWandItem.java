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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.List;

//import static hyperdoot5.freezingwand.client.FWClientEvents.replaceBlock;


public class FreezingWandItem extends Item {

    //Debug
    private static final Logger DEBUG = LogUtils.getLogger();

    public FreezingWandItem(Properties properties) {
        super(properties);
    }
    int blockSelPos = 1;
    int itemSelPos = 1;
    //Item Functionality
    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Variables for readability
        Player player = context.getPlayer();
        Level level = context.getLevel();
        Fluid water_flowing = Fluids.FLOWING_WATER.getFlowing();
        Fluid water_source = Fluids.WATER.getSource();
        BlockState ice_block = Blocks.ICE.defaultBlockState();
        BlockState packed_ice = Blocks.PACKED_ICE.defaultBlockState();
        BlockState blue_ice = Blocks.BLUE_ICE.defaultBlockState();
        BlockState snow_block = Blocks.SNOW_BLOCK.defaultBlockState();
        ItemStack ice_item = Items.ICE.getDefaultInstance();
        ItemStack packed_ice_item = Items.PACKED_ICE.getDefaultInstance();
        ItemStack blue_ice_item = Items.BLUE_ICE.getDefaultInstance();
        BlockPos clickedPos = context.getClickedPos();
        Direction cardinalDirection = context.getClickedFace();
        BlockState blockClicked = level.getBlockState(clickedPos);
        Fluid cardinalFluid = level.getFluidState(clickedPos.relative(cardinalDirection)).getType();

        //vars for selecting what ice block to place and collect
        BlockState selectedBlock = Blocks.ICE.defaultBlockState();
        ItemStack selectedItem = Items.ICE.getDefaultInstance();;
        boolean placedBlock = false;
        boolean replacedBlock = false;
        boolean iceCollected = false;


        // check if can place
        if (blockClicked == ice_block
                || blockClicked == packed_ice
                || blockClicked == blue_ice
                || blockClicked == snow_block
                || cardinalFluid == water_source
                || cardinalFluid == water_flowing) {
            placedBlock = true;
        }

        // change selected block
        switch(blockSelPos){
            case 1 -> selectedBlock = ice_block;
            case 2 -> selectedBlock = packed_ice;
            case 3 -> selectedBlock = blue_ice;
            case 4 -> blockSelPos = 1;
        }
        // change selected item to collect
        switch (itemSelPos){
            case 1 -> selectedItem = ice_item;
            case 2 -> selectedItem = packed_ice_item;
            case 3 -> selectedItem = blue_ice_item;
            case 4 -> itemSelPos = 1;
        }

        // LOGICAL SIDE EVENT
        //if try to place on a valid block, place selectedblock
        if (!level.isClientSide) {
            if (player.isShiftKeyDown() && blockClicked == selectedBlock) {
                level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
                player.spawnAtLocation(selectedItem);
                iceCollected = true;
            } else if (placedBlock) {
                switch (cardinalDirection) {
                    case UP -> level.setBlock(clickedPos.relative(Direction.UP), selectedBlock, 3);
                    case DOWN -> level.setBlock(clickedPos.relative(Direction.DOWN), selectedBlock, 3);
                    case NORTH -> level.setBlock(clickedPos.relative(Direction.NORTH), selectedBlock, 3);
                    case EAST -> level.setBlock(clickedPos.relative(Direction.EAST), selectedBlock, 3);
                    case SOUTH -> level.setBlock(clickedPos.relative(Direction.SOUTH), selectedBlock, 3);
                    case WEST -> level.setBlock(clickedPos.relative(Direction.WEST), selectedBlock, 3);
                }
                // if player is holding shift when rclick, replace the block they click
            } else if (player.isShiftKeyDown()) {
                level.setBlock(clickedPos, selectedBlock, 3);
                replacedBlock = true;
            }
        }
        // PHYSICAL SIDE EVENT
        //damage wand & play block place sound
        if (placedBlock) {
            // Varywand damage if not building directly from water source or flowing water
            if ((cardinalFluid == water_source) || (cardinalFluid == water_flowing)) {
                context.getItemInHand().hurtAndBreak(0, player, EquipmentSlot.MAINHAND);
            } else if (blockClicked != snow_block) {
                context.getItemInHand().hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
            } else {
                context.getItemInHand().hurtAndBreak(5, player, EquipmentSlot.MAINHAND);
            }
            level.playSound(player, clickedPos, SoundEvents.STONE_PLACE, SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        } else if (replacedBlock) {
            context.getItemInHand().hurtAndBreak(10, player, EquipmentSlot.MAINHAND);
            level.playSound(player, clickedPos, SoundEvents.AXOLOTL_SPLASH, SoundSource.PLAYERS);

            return InteractionResult.SUCCESS;
        } else if (iceCollected) {
            level.playSound(player, clickedPos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        } else {
            if(level.isClientSide && player != null && !player.isShiftKeyDown()) {
                String message = "The wand changes attunement";
                player.sendSystemMessage(Component.translatable(message));
                level.playSound(player, clickedPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS);
            }
            blockSelPos++;
            itemSelPos++;
            return InteractionResult.PASS;
        }
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
    // Cant break blocks with wand lol
    @Override
    public float getDestroySpeed(@NonNull ItemStack stack, BlockState state){
        return 0;
    }
    // custom tooltip
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag
            flags) {
        super.appendHoverText(stack, context, tooltip, flags);
        tooltip.add(Component.translatable("item.freezingwand.desc").withStyle(ChatFormatting.GRAY));
    }
}
