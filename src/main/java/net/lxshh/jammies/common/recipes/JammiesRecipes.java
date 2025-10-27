package net.lxshh.jammies.common.recipes;

import net.lxshh.jammies.Jammies;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JammiesRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Jammies.MOD_ID);

    public static final Supplier<RecipeSerializer<JamJarSealingRecipe>> JAM_SEALING_RECIPE =
            RECIPE_SERIALIZERS.register("jar_sealing", JamJarSealingRecipe.Serializer::new);

    public static final Supplier<RecipeSerializer<JamJarUnsealingRecipe>> JAM_UNSEALING_RECIPE =
            RECIPE_SERIALIZERS.register("jar_unsealing", JamJarUnsealingRecipe.Serializer::new);

}
