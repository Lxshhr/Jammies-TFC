package net.lxshh.jammies.data;

import com.eerussianguy.firmalife.FirmaLife;
import com.eerussianguy.firmalife.common.items.FLItems;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.ingredients.AndIngredient;
import net.dries007.tfc.common.recipes.ingredients.NotRottenIngredient;
import net.dries007.tfc.common.recipes.outputs.CopyFoodModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.kuba807.kubastfca.common.item.KubastfcaItems;
import net.kuba807.kubastfca.kubastfca;
import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {

    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // TFC Recipes
        TFCItems.UNSEALED_FRUIT_PRESERVES.forEach((food, item) ->
            sealingRecipe(item, TFCItems.FRUIT_PRESERVES.get(food), "crafting/jar/" + food.getSerializedName() + "_sealing", output)
        );

        TFCItems.FRUIT_PRESERVES.forEach((food, item) ->
            unsealingRecipe(item, TFCItems.UNSEALED_FRUIT_PRESERVES.get(food), "crafting/jar/" + food.getSerializedName() + "_unsealing", output)
        );

        JamJarSealingRecipeBuilder.sealing(
                        ModTags.Items.LIDS,
                        Ingredient.of(TFCItems.EMPTY_JAR),
                        ItemStackProvider.of(new ItemStack(TFCItems.EMPTY_JAR_WITH_LID)))
                .unlockedBy("has_jar", has(TFCItems.EMPTY_JAR))
                .save(output, ResourceLocation.fromNamespaceAndPath(Jammies.MOD_ID, "crafting/empty_jar_with_lid"));

        JamJarUnsealingRecipeBuilder.unSealing(
                Ingredient.of(TFCItems.EMPTY_JAR_WITH_LID),
                ItemStackProvider.of(new ItemStack(TFCItems.EMPTY_JAR)))
                .alwaysReturnLid()
                .unlockedBy("has_jar", has(TFCItems.EMPTY_JAR_WITH_LID))
                .save(output, Jammies.loc("crafting/empty_jar"));

        // Firmalife Compat
        FLItems.UNSEALED_FRUIT_PRESERVES.forEach((food, item) ->
                sealingRecipe(item, FLItems.FRUIT_PRESERVES.get(food), "crafting/jar/fl/" + food.getSerializedName() + "_sealing",
                        output.withConditions(modLoaded(FirmaLife.MOD_ID)))
        );

        FLItems.FRUIT_PRESERVES.forEach((food, item) ->
                unsealingRecipe(item, FLItems.UNSEALED_FRUIT_PRESERVES.get(food), "crafting/jar/fl/" + food.getSerializedName() + "_unsealing",
                        output.withConditions(modLoaded(FirmaLife.MOD_ID)))
        );

        // KubasTFC Compat
        sealingRecipe(KubastfcaItems.UNSEALED_MEAT_WEK, KubastfcaItems.MEAT_WEK, "crafting/jar/kubas/meat_close",
                output.withConditions(modLoaded(kubastfca.MODID)));

        sealingRecipe(KubastfcaItems.UNSEALED_MIX_WEK, KubastfcaItems.MIX_WEK, "crafting/jar/kubas/mix_close",
                output.withConditions(modLoaded(kubastfca.MODID)));

        sealingRecipe(KubastfcaItems.UNSEALED_VEGGIE_WEK, KubastfcaItems.VEGGIE_WEK, "crafting/jar/kubas/veggie_close",
                output.withConditions(modLoaded(kubastfca.MODID)));

        unsealingRecipe(KubastfcaItems.MEAT_WEK, KubastfcaItems.UNSEALED_MEAT_WEK, "crafting/jar/kubas/meat_open",
                output.withConditions(modLoaded(kubastfca.MODID)));

        unsealingRecipe(KubastfcaItems.MIX_WEK, KubastfcaItems.UNSEALED_MIX_WEK, "crafting/jar/kubas/mix_open",
                output.withConditions(modLoaded(kubastfca.MODID)));

        unsealingRecipe(KubastfcaItems.VEGGIE_WEK, KubastfcaItems.UNSEALED_VEGGIE_WEK, "crafting/jar/kubas/veggie_open",
                output.withConditions(modLoaded(kubastfca.MODID)));
    }

    private void sealingRecipe(ItemLike ingredient, ItemLike result, String rl, RecipeOutput output) {
        JamJarSealingRecipeBuilder.sealing(
                ModTags.Items.LIDS,
                notRotten(Ingredient.of(ingredient)),
                ItemStackProvider.of(new ItemStack(result), CopyFoodModifier.INSTANCE)
            )
            .unlockedBy("has_jar", has(ingredient))
            .save(output, Jammies.loc(rl));
    }

    private void unsealingRecipe(ItemLike ingredient, ItemLike result, String rl, RecipeOutput output) {
        JamJarUnsealingRecipeBuilder.unSealing(
                Ingredient.of(ingredient),
                ItemStackProvider.of(new ItemStack(result), CopyFoodModifier.INSTANCE)
        )
        .unlockedBy("has_jar", has(ingredient))
        .save(output, Jammies.loc(rl));
    }

    private Ingredient notRotten(Ingredient food) {
        return AndIngredient.of(food, NotRottenIngredient.INSTANCE);
    }
}
