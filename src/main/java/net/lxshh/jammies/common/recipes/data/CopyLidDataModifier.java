package net.lxshh.jammies.common.recipes.data;

import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.lxshh.jammies.registry.JammiesComponents;
import net.lxshh.jammies.registry.JammiesItemStackModifiers;
import net.minecraft.world.item.ItemStack;

public enum CopyLidDataModifier implements ItemStackModifier {
    INSTANCE;

    @Override
    public ItemStack apply(ItemStack itemStack, ItemStack input, Context context) {
        LidDataComponent component = input.get(JammiesComponents.JAR_LID_COMPONENT);

        if (component != null) {
            itemStack.set(JammiesComponents.JAR_LID_COMPONENT, component);
        }
        return itemStack;
    }

    @Override
    public ItemStackModifierType<?> type() {
        return JammiesItemStackModifiers.COPY_LID_MODIFIER.get();
    }
}
