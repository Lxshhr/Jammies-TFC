package net.lxshh.jammies.common.recipes.output;

import net.dries007.tfc.common.component.food.FoodCapability;
import net.dries007.tfc.common.component.food.IFood;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.lxshh.jammies.registry.JammiesItemStackModifiers;
import net.minecraft.world.item.ItemStack;

public enum CopyExactDateModifier implements ItemStackModifier {
    INSTANCE;

    @Override
    public ItemStack apply(ItemStack itemStack, ItemStack input, Context context) {
        IFood iFoodCaps = FoodCapability.get(input);
        if (iFoodCaps != null) {
            return FoodCapability.setCreationDate(itemStack, iFoodCaps.getCreationDate());
        }
        return itemStack;
    }

    @Override
    public ItemStackModifierType<?> type() {
        return JammiesItemStackModifiers.COPY_DATE_MODIFIER.get();
    }
}
