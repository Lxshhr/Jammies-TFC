package net.lxshh.jammies.common.util;

import net.dries007.tfc.util.data.DataManager;
import net.lxshh.jammies.Jammies;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ModDataManagers {
    public static final ResourceKey<Registry<DataManager<?>>> KEY = ResourceKey.createRegistryKey(Jammies.loc("data_manager"));
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
