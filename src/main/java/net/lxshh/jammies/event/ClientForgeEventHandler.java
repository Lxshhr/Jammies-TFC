package net.lxshh.jammies.event;

import net.lxshh.jammies.common.component.LidDataComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public final class ClientForgeEventHandler {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ClientForgeEventHandler::onItemToolTip);
    }

    public static void onItemToolTip(ItemTooltipEvent event) {
        final ItemStack stack = event.getItemStack();
        final List<Component> tooltip = event.getToolTip();

        if (!stack.isEmpty()) {
            LidDataComponent.addTooltipInfo(stack, tooltip);
        }
    }
}
