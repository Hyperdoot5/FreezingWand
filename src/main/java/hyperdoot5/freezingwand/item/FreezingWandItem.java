package hyperdoot5.freezingwand.item;

import com.mojang.logging.LogUtils;
import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.init.FWStats;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import static hyperdoot5.freezingwand.FreezingWandMod.DEBUG;
import static hyperdoot5.freezingwand.FreezingWandMod.prefix;

import java.util.List;

//import static hyperdoot5.freezingwand.client.FWClientEvents.replaceBlock;


public class FreezingWandItem extends Item {
    public static final ResourceLocation BASIC = prefix("basic");
    public static final ResourceLocation ICE = prefix("ice");
    public static final ResourceLocation PACKED_ICE = prefix("packed_ice");
    public static final ResourceLocation BLUE_ICE = prefix("blue_ice");

    public FreezingWandItem(Properties properties) {
        super(properties);
    }
    int blockSelPos = 1;
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
        if (blockSelPos == 4){blockSelPos = 1;}
        // change selected block
        switch(blockSelPos){
            case 1 -> {
                selectedBlock = ice_block;
                selectedItem = ice_item;
            }
            case 2 -> {
                selectedBlock = packed_ice;
                selectedItem = packed_ice_item;
            }
            case 3 -> {
                selectedBlock = blue_ice;
                selectedItem = blue_ice_item;
            }
        }

        // LOGICAL SERVER SIDE EVENT
        //if try to place on a valid block, place selectedblock
        if (!level.isClientSide && player != null) {
            if (player.isShiftKeyDown() && blockClicked == selectedBlock) {
                level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(selectedItem.getItem()));
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
        // PHYSICAL CLIENT SIDE EVENT
        //damage wand & play block place sound
        if (placedBlock) {
            // Varywand damage if not building directly from water source or flowing water
            if ((cardinalFluid == water_source) || (cardinalFluid == water_flowing)) {
                assert player != null;
                context.getItemInHand().hurtAndBreak(0, player, EquipmentSlot.MAINHAND);
            } else if (blockClicked != snow_block) {
                assert player != null;
                context.getItemInHand().hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
            } else {
                assert player != null;
                context.getItemInHand().hurtAndBreak(5, player, EquipmentSlot.MAINHAND);
            }
            level.playSound(player, clickedPos, SoundEvents.STONE_PLACE, SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        } else if (replacedBlock) {
            context.getItemInHand().hurtAndBreak(10, player, EquipmentSlot.MAINHAND);
            level.playSound(player, clickedPos, SoundEvents.AXOLOTL_SPLASH, SoundSource.PLAYERS);

            return InteractionResult.SUCCESS;
        } else if (iceCollected) {
            switch(blockSelPos){
                case 1 -> {
                    context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                    player.awardStat(FWStats.ICE_COLLECTED.get());
                }
                case 2 -> {
                    context.getItemInHand().hurtAndBreak(10, player, EquipmentSlot.MAINHAND);
                    player.awardStat(FWStats.PACKED_ICE_COLLECTED.get());
                }
                case 3 -> {
                    context.getItemInHand().hurtAndBreak(100, player, EquipmentSlot.MAINHAND);
                    player.awardStat(FWStats.BLUE_ICE_COLLECTED.get());
                }
            }
            level.playSound(player, clickedPos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        } else {
            if(level.isClientSide && player != null && !player.isShiftKeyDown()) {
                String message = "The wand changes attunement";
                player.sendSystemMessage(Component.translatable(message));
                level.playSound(player, clickedPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS);
            }
            // Cannot have inside above if statement -> for server compatability);
            blockSelPos++;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, context, tooltip, flags);
        // add a way to grab the attunement to add to tooltip
        tooltip.add(Component.translatable("item.freezingwand.desc").withStyle(ChatFormatting.GRAY));
    }
    /*
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        return InteractionResult.PASS;
    }

    public void postHurtEnemy(ItemStack p_346136_, LivingEntity p_346250_, LivingEntity p_346014_) {
    }
    */
}
