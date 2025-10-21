package net.lxshh.jammies.datagen;

import net.lxshh.jammies.common.recipes.JamJarUnsealingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class JamJarUnsealingRecipeBuilder implements RecipeBuilder {
    private final Ingredient jar;
    private final ItemStack result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public JamJarUnsealingRecipeBuilder(Ingredient jar, ItemLike result) {
        this.jar = jar;
        this.result = new ItemStack(result);
    }

    public static JamJarUnsealingRecipeBuilder unSealing(Ingredient jar, ItemLike result) {
        return new JamJarUnsealingRecipeBuilder(jar, result);
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    protected Recipe<?> recipe() {
        return new JamJarUnsealingRecipe(jar, result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
        ensureValid(recipeId);

        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement::addCriterion);

        recipeOutput.accept(recipeId, recipe(), advancement.build(recipeId.withPrefix("recipes/crafting/jar")));
    }

    protected void ensureValid(ResourceLocation recipeId) {
        if (criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
