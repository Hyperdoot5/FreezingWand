package hyperdoot5.freezingwand.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;



public class FreezingWandItem extends Item {

    public FreezingWandItem(Item.Properties properties) {
        super(properties);
    }
    // When player right clicks, place ice block on the face of the block they clicked if that face is next to air
    // currently places a block above where player right clicks and also replaces the block that is clicked,
    // if the block is underwater then it places a block on the surface and replaces the block that is clicked
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide()) {
            BlockState iceBlockState = Blocks.ICE.defaultBlockState();
            context.getLevel().setBlock(context.getClickedPos(), iceBlockState, 3);
            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, context, tooltip, flags);
        tooltip.add(Component.translatable("Epic Description", stack.getMaxDamage() - stack.getDamageValue()).withStyle(ChatFormatting.GRAY));
    }
}

