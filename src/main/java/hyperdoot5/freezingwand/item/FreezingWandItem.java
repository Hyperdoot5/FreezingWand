package hyperdoot5.freezingwand.item;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.sounds.SoundEngineExecutor;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.sound.PlaySoundSourceEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.List;

import static net.minecraft.sounds.SoundEvents.ANVIL_LAND;
import static net.minecraft.sounds.SoundEvents.SNOW_BREAK;
import static net.neoforged.neoforge.client.ClientHooks.playSound;

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
        BlockState water_source = Blocks.WATER.defaultBlockState();
        Direction direction = context.getClickedFace();
        BlockPos position = context.getClickedPos();

        if ((!context.getLevel().isClientSide())
                && (context.getPlayer() != null)
                && (context.getLevel().getBlockState(context.getClickedPos().relative(direction)) == water_source)) {

            BlockState iceBlockState = Blocks.ICE.defaultBlockState();
            switch(context.getClickedFace()){
                case UP -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.UP), iceBlockState, 3);
                case DOWN -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.DOWN), iceBlockState, 3);
                case NORTH -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.NORTH), iceBlockState, 3);
                case EAST -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.EAST), iceBlockState, 3);
                case SOUTH -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.SOUTH), iceBlockState, 3);
                case WEST -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.WEST), iceBlockState, 3);
            }
//            context.getPlayer().playSound(SoundEvents.SNOW_BREAK,1.0F,2.0F); //play sound on interact NOT WORKING ATM
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
        tooltip.add(Component.translatable("Epic Description").withStyle(ChatFormatting.GRAY));
    }
}

