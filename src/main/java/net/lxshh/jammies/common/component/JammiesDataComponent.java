package net.lxshh.jammies.common.component;

import net.lxshh.jammies.Jammies;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JammiesDataComponent {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Jammies.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LidDataComponent>> JAR_LID_COMPONENT =
            DATA_COMPONENTS.register(
                    "lid", () ->
                        DataComponentType.<LidDataComponent>builder()
                            .persistent(LidDataComponent.CODEC)
                            .networkSynchronized(LidDataComponent.STREAM_CODEC)
                            .build()
            );
}
