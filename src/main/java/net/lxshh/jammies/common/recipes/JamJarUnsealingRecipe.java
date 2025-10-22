package net.lxshh.jammies.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lxshh.jammies.common.component.JammiesDataComponent;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class JamJarUnsealingRecipe implements CraftingRecipe {
    private final Ingredient sealedJar;
    private final ItemStack result;
    private final Boolean alwaysReturnLid;

    public JamJarUnsealingRecipe(Ingredient sealedJar, ItemStack result, Boolean alwaysReturnLid) {
        this.sealedJar = sealedJar;
        this.result = result;
        this.alwaysReturnLid = alwaysReturnLid;
    }

    public JamJarUnsealingRecipe(Ingredient sealedJar, ItemStack result) {
        this(sealedJar, result, false);
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
        LidDataComponent component = itemStack.get(JammiesDataComponent.JAR_LID_COMPONENT);
        assert component != null;
        Item lidItem = component.lidStack().copy().getItem();
        float returnChance = component.returnChance();

        if (this.alwaysReturnLid || randomSource.nextFloat() < returnChance) {
            return new ItemStack(lidItem, 1);
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
                ItemStack.CODEC.fieldOf("result").forGetter(c -> c.result),
                Codec.BOOL.optionalFieldOf("always_return_lid", false).forGetter(c -> c.alwaysReturnLid)
        ).apply(i, JamJarUnsealingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, JamJarUnsealingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, c -> c.sealedJar,
                ItemStack.STREAM_CODEC, c -> c.result,
                ByteBufCodecs.BOOL, c -> c.alwaysReturnLid,
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
