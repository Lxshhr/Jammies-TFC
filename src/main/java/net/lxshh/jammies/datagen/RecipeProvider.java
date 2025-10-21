package net.lxshh.jammies.datagen;

import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.ingredients.AndIngredient;
import net.dries007.tfc.common.recipes.ingredients.NotRottenIngredient;
import net.lxshh.jammies.tags.JammiesTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {

    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        TFCItems.UNSEALED_FRUIT_PRESERVES.forEach((food, item) ->
                JamJarSealingRecipeBuilder.sealing(
                                JammiesTags.Items.LIDS,
                                notRotten(Ingredient.of(item)),
                                TFCItems.FRUIT_PRESERVES.get(food)
                        )
                        .unlockedBy("has_jar", has(item))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("jammies", "crafting/jar/" + food.getSerializedName() + "_sealing"))
        );

        TFCItems.FRUIT_PRESERVES.forEach((food, item) ->
                JamJarUnsealingRecipeBuilder.unSealing(
                                notRotten(Ingredient.of(item)),
                                TFCItems.UNSEALED_FRUIT_PRESERVES.get(food)
                        )
                        .unlockedBy("has_jar", has(item))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("jammies", "crafting/jar/" + food.getSerializedName() + "_unsealing"))
        );

        JamJarSealingRecipeBuilder.sealing(JammiesTags.Items.LIDS, Ingredient.of(TFCItems.EMPTY_JAR), TFCItems.EMPTY_JAR_WITH_LID);
    }

    private Ingredient notRotten(Ingredient food) {
        return AndIngredient.of(food, NotRottenIngredient.INSTANCE);
    }
}
