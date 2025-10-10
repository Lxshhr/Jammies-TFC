package net.lxshh.jammies.common.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.items.TFCItems;
import net.lxshh.jammies.common.util.JammiesDataComponent;
import net.lxshh.jammies.common.util.data.LidDataComponent;
import net.lxshh.jammies.common.items.JammiesItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class JamJarUnsealingRecipe implements CraftingRecipe {
    private final Ingredient sealedJar;
    private final ItemStack result;

    public JamJarUnsealingRecipe(Ingredient sealedJar, ItemStack result) {
        this.sealedJar = sealedJar;
        this.result = result;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        boolean hasSealedJar = false;
        int itemCount = 0;
        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack stack = craftingInput.getItem(i);
            if (!stack.isEmpty()) {
                itemCount ++;
                if (sealedJar.test(stack) && stack.has(JammiesDataComponent.JAR_LID_COMPONENT)) {
                    hasSealedJar = true;
                }
            }
        }
        return itemCount == 1 && hasSealedJar;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        ingredients.add(sealedJar);
        return ingredients;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && sealedJar.test(stack) && stack.has(JammiesDataComponent.JAR_LID_COMPONENT)) {
                RandomSource randomSource = RandomSource.create();
                ItemStack lidReturn =  getReturnLid(stack, randomSource);
                if (!lidReturn.isEmpty()) {
                    remainingItems.set(i, lidReturn);
                }
            }
        }
        return remainingItems;
    }

    public ItemStack getReturnLid(ItemStack itemStack, RandomSource randomSource) {
        LidDataComponent component = itemStack.get(JammiesDataComponent.JAR_LID_COMPONENT.get());
        assert component != null;
        Item lidItem = component.lidStack().copy().getItem();

        if (lidItem == TFCItems.JAR_LID.get()) {
            // 50% Chance
            if (randomSource.nextFloat() > 0.5F) {
                return new ItemStack(TFCItems.JAR_LID.get(), 1);
            }
        } else if (lidItem == JammiesItems.ALUMINIUM_LID.get()) {
            // 80% Chance
            if (randomSource.nextFloat() < 0.8F) {
                return new ItemStack(JammiesItems.ALUMINIUM_LID.get(), 1);
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return JammiesRecipeSerializers.JAM_UNSEALING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<JamJarUnsealingRecipe> {
        public static final MapCodec<JamJarUnsealingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Ingredient.CODEC.fieldOf("jar").forGetter(c -> c.sealedJar),
                ItemStack.CODEC.fieldOf("result").forGetter(c -> c.result)
        ).apply(i, JamJarUnsealingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, JamJarUnsealingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, c -> c.sealedJar,
                ItemStack.STREAM_CODEC, c -> c.result,
                JamJarUnsealingRecipe::new
        );

        @Override
        public MapCodec<JamJarUnsealingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, JamJarUnsealingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
