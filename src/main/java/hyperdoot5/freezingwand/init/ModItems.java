package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.item.FreezingWandItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import static hyperdoot5.freezingwand.FreezingWand.MODID;

public class ModItems {
    // Create a Deferred Register to hold Items which will all be registered under the "freezingwand" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    // Test Item registering, fingers crossed
    public static final DeferredItem<Item> FREEZING_WAND = ITEMS.register(
            "freezing_wand", () -> new FreezingWandItem( new Item.Properties()
                    .durability(100)
                    .stacksTo(1)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)){

                //from TFItems
//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        return context.getLevel().getBlockState(context.getClickedPos()).is(this.getBlock()) ? super.useOn(context) : InteractionResult.PASS;
//    }

                @Override
                public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
                    BlockHitResult fluidHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
                    BlockHitResult placeBlockResult = fluidHitResult.withPosition(fluidHitResult.getBlockPos().above());
                    InteractionResult result = super.useOn(new UseOnContext(player, hand, placeBlockResult));
                    return new InteractionResultHolder<>(result, player.getItemInHand(hand));
                }});
}
