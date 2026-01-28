package net.lxshh.jammies;

import net.lxshh.jammies.common.component.ModComponents;
import net.lxshh.jammies.common.recipes.ModRecipes;
import net.lxshh.jammies.common.util.ModDataManagers;
import net.lxshh.jammies.config.ClientConfig;
import net.lxshh.jammies.config.CommonConfig;
import net.lxshh.jammies.event.ClientEventHandler;
import net.lxshh.jammies.event.EventHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Jammies.MOD_ID)
public class Jammies {
    public static final String MOD_ID = "jammies";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Jammies(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        ModComponents.DATA_COMPONENTS.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        ModDataManagers.DATA_MANAGERS.register(modEventBus);

        modEventBus.addListener(this::onNewRegistry);
        modEventBus.addListener(EventHandler::registerPayloadHandler);

        NeoForge.EVENT_BUS.addListener(EventHandler::addReloadListeners);
        NeoForge.EVENT_BUS.addListener(EventHandler::onDataPackSync);

        if (dist == Dist.CLIENT) {
            ClientEventHandler.init(NeoForge.EVENT_BUS);
        }

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);
    }

    private void onNewRegistry(NewRegistryEvent event) {
        event.register(ModDataManagers.REGISTRY);
    }

    public static ResourceLocation loc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
