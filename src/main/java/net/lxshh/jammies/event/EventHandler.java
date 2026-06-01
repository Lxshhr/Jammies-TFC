package net.lxshh.jammies.event;

import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.network.ModDataManagerSyncPacket;
import net.lxshh.jammies.common.data.ModDataManagers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class EventHandler {

    public static void addReloadListeners(AddReloadListenerEvent event) {
        ModDataManagers.REGISTRY.forEach(event::addListener);
    }

    public static void onDataPackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            PacketDistributor.sendToAllPlayers(new ModDataManagerSyncPacket());
        } else {
            PacketDistributor.sendToPlayer(event.getPlayer(), new ModDataManagerSyncPacket());
        }
    }

    public static void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Jammies.MOD_ID);

        registrar.playToClient(ModDataManagerSyncPacket.TYPE, ModDataManagerSyncPacket.CODEC, (packet, context) -> context.enqueueWork(() -> packet.handle(context.connection().isMemoryConnection())));
    }

}
