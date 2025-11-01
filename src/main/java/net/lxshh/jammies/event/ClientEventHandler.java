package net.lxshh.jammies.event;

import net.lxshh.jammies.common.component.LidDataComponent;
import net.lxshh.jammies.common.util.LidProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public final class ClientEventHandler {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ClientEventHandler::onItemToolTip);
    }

    public static void onItemToolTip(ItemTooltipEvent event) {
        final ItemStack stack = event.getItemStack();
        final List<Component> tooltip = event.getToolTip();

        if (!stack.isEmpty()) {
            LidDataComponent.addTooltipInfo(stack, tooltip);
        }

        for (LidProperties props : LidProperties.MANAGER.getValues()) {
            ItemStack[] items = props.lidItem().getItems();
            for (ItemStack lidStack : items) {
                if (!lidStack.isEmpty() && stack.is(lidStack.getItem())) {
                    float returnChance = LidProperties.getReturnChance(stack);
                    tooltip.add(1, Component.translatable("tooltip.jammies.lid.return_chance", String.format("%.0f%%", returnChance * 100)));
                }
            }
        }
    }
}
