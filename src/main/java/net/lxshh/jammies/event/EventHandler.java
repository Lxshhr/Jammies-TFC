package net.lxshh.jammies.event;

import net.lxshh.jammies.common.util.data.JammiesDataManagers;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

public class EventHandler {
    public static void addReloadListeners(AddReloadListenerEvent event) {
        JammiesDataManagers.REGISTRY.forEach(event::addListener);
    }
    public static void onDataPackSync(OnDatapackSyncEvent event) {

    }
}
