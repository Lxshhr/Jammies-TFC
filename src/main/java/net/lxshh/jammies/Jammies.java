package net.lxshh.jammies;

import net.lxshh.jammies.common.util.JammiesDataComponent;
import net.lxshh.jammies.common.util.data.JammiesDataManagers;
import net.lxshh.jammies.common.items.JammiesItems;
import net.lxshh.jammies.common.recipes.JammiesRecipeSerializers;
import net.lxshh.jammies.event.TooltipEvent;
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
        JammiesDataManagers.MANAGERS.register(modEventBus);
        JammiesRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::registerRegistries);

        if (dist == Dist.CLIENT) {
            TooltipEvent.init(NeoForge.EVENT_BUS);
        }
    }

    public void registerRegistries(NewRegistryEvent event) {
        event.register(JammiesDataManagers.REGISTRY);
    }
}
