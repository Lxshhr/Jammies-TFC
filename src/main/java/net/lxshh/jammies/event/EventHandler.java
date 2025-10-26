package net.lxshh.jammies.event;

import net.dries007.tfc.common.TFCCreativeTabs;
import net.dries007.tfc.common.items.TFCItems;
import net.lxshh.jammies.Jammies;
import net.lxshh.jammies.common.component.JammiesDataComponent;
import net.lxshh.jammies.common.component.LidDataComponent;
import net.lxshh.jammies.common.items.JammiesItems;
import net.lxshh.jammies.common.util.JammiesDataManagerSyncPacket;
import net.lxshh.jammies.common.util.JammiesDataManagers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class EventHandler {

    public static void addReloadListeners(AddReloadListenerEvent event) {
        JammiesDataManagers.REGISTRY.forEach(event::addListener);
    }

    public static void onDataPackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            PacketDistributor.sendToAllPlayers(new JammiesDataManagerSyncPacket());
        } else {
            PacketDistributor.sendToPlayer(event.getPlayer(), new JammiesDataManagerSyncPacket());
        }
    }

    public static void registerPayloadHandler(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Jammies.MOD_ID);

        registrar.playToClient(JammiesDataManagerSyncPacket.TYPE, JammiesDataManagerSyncPacket.CODEC, (packet, context) -> context.enqueueWork(() -> packet.handle(context.connection().isMemoryConnection())));
    }

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(TFCCreativeTabs.MISC.tab().getKey())) {
            event.accept(JammiesItems.ALUMINIUM_LID.get());
        }
    }

    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modify(TFCItems.EMPTY_JAR_WITH_LID, i -> i.set(JammiesDataComponent.JAR_LID_COMPONENT.get(), LidDataComponent.of(new ItemStack(TFCItems.JAR_LID.get()))));
    }
}
