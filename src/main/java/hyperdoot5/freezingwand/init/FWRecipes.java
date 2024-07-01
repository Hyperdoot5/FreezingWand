package hyperdoot5.freezingwand.init;

import hyperdoot5.freezingwand.item.repair.FreezingWandRepairRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static hyperdoot5.freezingwand.FreezingWandMod.MODID;

public class FWRecipes {
    //Create a DeferredRegister tp hold Recipes which will all be registered
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    //Recipe registering
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FreezingWandRepairRecipe>> FREEZING_WAND_REPAIR_RECIPE = FWRecipes.RECIPE_SERIALIZERS.register("freezing_wand_repair_recipe", () -> new SimpleCraftingRecipeSerializer<>(FreezingWandRepairRecipe::new));
}