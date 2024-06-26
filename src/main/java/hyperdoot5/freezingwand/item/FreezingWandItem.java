package hyperdoot5.freezingwand.item;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

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
        if (!context.getLevel().isClientSide()
                && (context.getPlayer() != null)
                && (context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace())) == Blocks.WATER.defaultBlockState())) {

            BlockState iceBlockState = Blocks.ICE.defaultBlockState();
            switch(context.getClickedFace()){
                case UP -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.UP), iceBlockState, 3);
                case DOWN -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.DOWN), iceBlockState, 3);
                case NORTH -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.NORTH), iceBlockState, 3);
                case EAST -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.EAST), iceBlockState, 3);
                case SOUTH -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.SOUTH), iceBlockState, 3);
                case WEST -> context.getLevel().setBlock(context.getClickedPos().relative(Direction.WEST), iceBlockState, 3);
            }

            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
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

