package net.lxshh.jammies.common.util;

import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.common.util.data.LidProperties;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = Jammies.MOD_ID)
public class JammiesRegistries {
    public static final ResourceKey<Registry<LidProperties>> LID_PROPERTIES = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Jammies.MOD_ID, "lid_properties"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                LID_PROPERTIES,
                LidProperties.CODEC,
                LidProperties.CODEC
        );
    }
}
