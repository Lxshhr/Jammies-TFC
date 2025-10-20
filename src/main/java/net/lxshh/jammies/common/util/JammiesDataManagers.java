package net.lxshh.jammies.common.util;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.data.DataManager;
import net.dries007.tfc.util.data.DataManagers;
import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.common.util.data.LidProperties;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class JammiesDataManagers {
    public static final ResourceKey<Registry<DataManager<?>>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Jammies.MOD_ID, "data_manager"));
    public static final Registry<DataManager<?>> REGISTRY = new RegistryBuilder<>(KEY).sync(true).create();

    public static final DeferredRegister<DataManager<?>> DATA_MANAGERS = DeferredRegister.create(KEY, Jammies.MOD_ID);

    static {
        register(LidProperties.MANAGER);
    }

    private static void register(DataManager<?> manager)
    {
        DATA_MANAGERS.register(manager.getName(), () -> manager);
    }

}
