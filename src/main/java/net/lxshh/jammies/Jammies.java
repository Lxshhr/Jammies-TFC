package net.lxshh.jammies;

import net.lxshh.jammies.common.component.JammiesDataComponent;
import net.lxshh.jammies.common.items.JammiesItems;
import net.lxshh.jammies.common.recipes.JammiesRecipeSerializers;
import net.lxshh.jammies.common.util.JammiesDataManagers;
import net.lxshh.jammies.event.ClientEventHandler;
import net.lxshh.jammies.event.EventHandler;
import net.neoforged.api.distmarker.Dist;
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
        JammiesItems.ITEMS.register(modEventBus);

        JammiesDataComponent.DATA_COMPONENTS.register(modEventBus);
        JammiesRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        JammiesDataManagers.DATA_MANAGERS.register(modEventBus);

        modEventBus.addListener(this::onNewRegistry);
        modEventBus.addListener(EventHandler::registerPayloadHandler);
        modEventBus.addListener(EventHandler::buildContents);
        modEventBus.addListener(EventHandler::modifyDefaultComponents);

        NeoForge.EVENT_BUS.addListener(EventHandler::addReloadListeners);
        NeoForge.EVENT_BUS.addListener(EventHandler::onDataPackSync);

        if (dist == Dist.CLIENT) {
            ClientEventHandler.init(NeoForge.EVENT_BUS);
        }
    }

    private void onNewRegistry(NewRegistryEvent event) {
        event.register(JammiesDataManagers.REGISTRY);
    }

}
