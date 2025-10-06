package net.lxshh.jammies;

import net.lxshh.jammies.common.data.JammiesDataComponent;
import net.lxshh.jammies.common.items.JammiesItems;
import net.lxshh.jammies.common.recipes.JammiesRecipeSerializers;
import net.lxshh.jammies.event.TooltipEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
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

        if (dist == Dist.CLIENT) {
            TooltipEvent.init(NeoForge.EVENT_BUS);
        }
    }
}
