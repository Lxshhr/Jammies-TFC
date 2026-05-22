package net.lxshh.jammies.common.recipes.output;

import com.mojang.serialization.MapCodec;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifierType;
import net.dries007.tfc.common.recipes.outputs.ItemStackModifiers;
import net.lxshh.jammies.Jammies;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemStackModifiers {
    public static final DeferredRegister<ItemStackModifierType<?>> ITEMSTACK_MODIFIER = DeferredRegister.create(ItemStackModifiers.KEY, Jammies.MOD_ID);

    public static final Supplier<ItemStackModifierType<CopyExactDateModifier>> COPY_DATE_MODIFIER = register("copy_date", CopyExactDateModifier.INSTANCE);
    public static final Supplier<ItemStackModifierType<CopyLidDataModifier>> COPY_LID_MODIFIER = register("copy_lid", CopyLidDataModifier.INSTANCE);

    private static <T extends ItemStackModifier> Supplier<ItemStackModifierType<T>> register(String name, T singleInstance) {
        return ITEMSTACK_MODIFIER.register(name, () -> new ItemStackModifierType<>(MapCodec.unit(singleInstance), StreamCodec.unit(singleInstance)));
    }
}
