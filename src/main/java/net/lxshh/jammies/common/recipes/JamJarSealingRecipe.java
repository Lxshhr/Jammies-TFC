package net.lxshh.jammies.common.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lxshh.jammies.common.component.JammiesDataComponent;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class JamJarSealingRecipe implements CraftingRecipe {
    private final Ingredient lid;
    private final Ingredient jar;
    private final ItemStack result;

    public JamJarSealingRecipe(Ingredient lid, Ingredient jar, ItemStack result) {
        this.lid = lid;
        this.jar = jar;
        this.result = result;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        boolean hasLid = false, hasJar = false;
        int itemCount = 0;
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (!stack.isEmpty()) {
                itemCount++;
                if (lid.test(stack)) hasLid = true;
                else if (jar.test(stack)) hasJar = true;
            }
        }
        return itemCount == 2 && hasLid && hasJar;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        ItemStack lid = ItemStack.EMPTY;
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (this.lid.test(stack)) {
                lid = stack;
            }
        }
        ItemStack result = getResultItem(provider).copy();
        LidDataComponent component = LidDataComponent.of(lid);
        if (component != null) {
            result.set(JammiesDataComponent.JAR_LID_COMPONENT, component);
        }
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(lid);
        ingredients.add(jar);
        return ingredients;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        return NonNullList.withSize(input.size(), ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return JammiesRecipeSerializers.JAM_SEALING_RECIPE.get();
    }


    public static class Serializer implements RecipeSerializer<JamJarSealingRecipe> {
        public static final MapCodec<JamJarSealingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Ingredient.CODEC.fieldOf("lid").forGetter(c -> c.lid),
                Ingredient.CODEC.fieldOf("jar").forGetter(c -> c.jar),
                ItemStack.CODEC.fieldOf("result").forGetter(c -> c.result)
        ).apply(i, JamJarSealingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, JamJarSealingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, c -> c.lid,
                Ingredient.CONTENTS_STREAM_CODEC, c -> c.jar,
                ItemStack.STREAM_CODEC, c -> c.result,
                JamJarSealingRecipe::new
        );

        @Override
        public MapCodec<JamJarSealingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, JamJarSealingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
