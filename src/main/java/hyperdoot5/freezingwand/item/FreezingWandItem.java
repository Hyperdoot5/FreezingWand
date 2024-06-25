package hyperdoot5.freezingwand.item;

import hyperdoot5.freezingwand.FreezingWand;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.common.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.minecraft.commands.arguments.blocks.BlockStateArgument.getBlock;


public class FreezingWandItem extends Item {

    public FreezingWandItem(Item.Properties properties) {
        super(properties);
    }
    // Test Code from Twilight Forest/item/FortificationWandItem
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getDamageValue() == stack.getMaxDamage() && !player.getAbilities().instabuild) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide()) {
            if(!player.getAbilities().instabuild) {
                stack.hurtAndBreak(1, (ServerLevel) level, player, item -> {});
            }
        }


        if (!player.isCreative())
            player.getCooldowns().addCooldown(this, 1200);

        return InteractionResultHolder.success(stack);
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
        tooltip.add(Component.translatable("item.twilightforest.scepter.desc", stack.getMaxDamage() - stack.getDamageValue()).withStyle(ChatFormatting.GRAY));
    }
}










    // ChatGPT Code
    // tring to add right click function to create an ice block on face of block that is clicked, and underwater
//    @Override
//    public @NotNull InteractionResult useOn(UseOnContext context) {
//        Level world = context.getLevel();
//        BlockPos pos = context.getClickedPos();
//        Player player = context.getPlayer();
//
//        if (!world.isClientSide && player != null && context.getHand() == InteractionHand.MAIN_HAND) {
//            world.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
//            return InteractionResult.SUCCESS;
//        }
//
//        return InteractionResult.FAIL;
//    }

