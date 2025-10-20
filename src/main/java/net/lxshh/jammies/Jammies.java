package net.lxshh.jammies;

import net.dries007.tfc.util.data.DataManager;
import net.dries007.tfc.util.data.DataManagers;
import net.lxshh.jammies.common.util.JammiesDataComponent;
import net.lxshh.jammies.common.items.JammiesItems;
import net.lxshh.jammies.common.recipes.JammiesRecipeSerializers;
import net.lxshh.jammies.common.util.JammiesDataManagerSyncPacket;
import net.lxshh.jammies.common.util.JammiesDataManagers;
import net.lxshh.jammies.common.util.data.LidProperties;
import net.lxshh.jammies.event.TooltipEvent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
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
        modEventBus.addListener(this::registerPayloadHandler);

        NeoForge.EVENT_BUS.addListener(this::addReloadListeners);
        NeoForge.EVENT_BUS.addListener(this::onDataPackSync);

        if (dist == Dist.CLIENT) {
            TooltipEvent.init(NeoForge.EVENT_BUS);
        }
    }

    private void onNewRegistry(NewRegistryEvent event) {
        event.register(JammiesDataManagers.REGISTRY);
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        JammiesDataManagers.REGISTRY.forEach(event::addListener);
    }

    private void onDataPackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            for (ServerPlayer player : event.getPlayerList().getPlayers()) {
                player.connection.send(new JammiesDataManagerSyncPacket());
            }
        } else {
            event.getPlayer().connection.send(new JammiesDataManagerSyncPacket());
        }
    }

    private void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);

        registrar.playToClient(JammiesDataManagerSyncPacket.TYPE, JammiesDataManagerSyncPacket.CODEC, (packet, context) -> context.enqueueWork(() -> packet.handle(context.connection().isMemoryConnection())));
    }

}
