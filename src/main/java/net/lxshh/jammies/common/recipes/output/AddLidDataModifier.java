package net.lxshh.jammies.common.recipes.output;

import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.lxshh.jammies.registry.JammiesComponents;
import net.lxshh.jammies.registry.JammiesItemStackModifiers;
import net.lxshh.jammies.registry.JammiesTags;
import net.minecraft.world.item.ItemStack;

public enum AddLidDataModifier implements ItemStackModifier {
    INSTANCE;

    @Override
    public ItemStack apply(ItemStack itemStack, ItemStack input, Context context) {
        if (input.is(JammiesTags.Items.LIDS)) {
            LidDataComponent component = LidDataComponent.of(input);
            if (component != null) {
                itemStack.set(JammiesComponents.JAR_LID_COMPONENT, component);
            }
        }
        return itemStack;
    }

    @Override
    public ItemStackModifierType<?> type() {
        return JammiesItemStackModifiers.ADD_LID_MODIFIER.get();
    }
}
