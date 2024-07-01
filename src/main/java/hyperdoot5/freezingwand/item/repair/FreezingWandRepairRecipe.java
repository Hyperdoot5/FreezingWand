package hyperdoot5.freezingwand.item.repair;

import hyperdoot5.freezingwand.init.FWItems;
import hyperdoot5.freezingwand.init.FWRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class FreezingWandRepairRecipe extends CustomRecipe {
    // Follows abstract class RepairItemRecipe.java
    //net\minecraft\world\item\crafting\RepairItemRecipe.java
    public FreezingWandRepairRecipe(CraftingBookCategory category){
        super(category);
    }
    // variables for added readability
    Item iceBlockItem = Blocks.ICE.asItem();
    Item packed_iceBlockItem = Blocks.PACKED_ICE.asItem();
    Item blue_iceBlockItem = Blocks.BLUE_ICE.asItem();

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack wand = null;
        List<ItemStack> ice = new ArrayList<>();

        for (int i = 0; i < input.size(); ++i) {
            ItemStack stackInQuestion = input.getItem(i);
            if (!stackInQuestion.isEmpty()) {
                if (stackInQuestion.is(FWItems.FREEZING_WAND.get()) && stackInQuestion.isDamaged()) {
                    wand = stackInQuestion;
                }
                if (stackInQuestion.is(iceBlockItem) || stackInQuestion.is(packed_iceBlockItem) || stackInQuestion.is(blue_iceBlockItem)) {
                    ice.add(stackInQuestion);
                }
            }
        }
        return wand != null && !ice.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup. Provider access) {
        List<Item> iceList = new ArrayList<>();
        List<Item> packed_iceList = new ArrayList<>();
        List<Item> blue_iceList = new ArrayList<>();
        ItemStack wand = null;
        for (int i = 0; i < input.size(); ++i) {
            ItemStack itemstack = input.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.is(FWItems.FREEZING_WAND.get())) {
                    if (wand == null) {
                        wand = itemstack;
                    } else {
                        //Only accept 1 wand
                        return ItemStack.EMPTY;
                    }
                }

                if (itemstack.is(iceBlockItem)) {
                    //add all iceList in the grid to a list to determine the amount to repair
                    iceList.add(itemstack.getItem());
                } else if (itemstack.is(packed_iceBlockItem)) {
                    //add all packed_iceList in the grid to a list to determine the amount to repair
                    packed_iceList.add(itemstack.getItem());
                } else if (itemstack.is(blue_iceBlockItem)) {
                    //add all blue_iceList in the grid to a list to determine the amount to repair
                    blue_iceList.add(itemstack.getItem());
                }
            }
        }

        if ((!iceList.isEmpty() || !packed_iceList.isEmpty() || !blue_iceList.isEmpty()) && wand != null && wand.isDamaged()) {
            ItemStack newWand = FWItems.FREEZING_WAND.get().getDefaultInstance();
            if(!iceList.isEmpty()) {
                //each block of iceList repairs 1 durability
                newWand.setDamageValue(wand.getDamageValue() - (iceList.size()));
            } else if(!packed_iceList.isEmpty()) {
                //each block of packed_iceList repairs 10 durability
                newWand.setDamageValue(wand.getDamageValue() - (packed_iceList.size() * 10));
            } else {
                //each block of blue_iceList repairs 100 durability
                newWand.setDamageValue(wand.getDamageValue() - (blue_iceList.size() * 100));
            }
            return newWand;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FWRecipes.FREEZING_WAND_REPAIR_RECIPE.get();
    }
}
