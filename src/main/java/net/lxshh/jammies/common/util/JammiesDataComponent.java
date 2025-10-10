package net.lxshh.jammies.common.util;

import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.common.util.data.LidDataComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JammiesDataComponent {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Jammies.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LidDataComponent>> JAR_LID_COMPONENT =
            DATA_COMPONENTS.registerComponentType(
                    "lid",
                    builder -> builder
                            .persistent(LidDataComponent.CODEC)
                            .networkSynchronized(LidDataComponent.STREAM_CODEC))
            ;
}
