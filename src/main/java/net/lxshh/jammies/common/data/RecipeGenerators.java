package net.lxshh.jammies.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerators extends VanillaRecipeProvider {
    public RecipeGenerators(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public void remove(String name) {
        final ResourceLocation id = ResourceLocation.fromNamespaceAndPath("tfc", name);

    }

}
