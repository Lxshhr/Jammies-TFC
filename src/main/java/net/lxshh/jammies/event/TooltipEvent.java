package net.lxshh.jammies.event;

import net.lxshh.jammies.common.util.JammiesDataComponent;
import net.lxshh.jammies.common.util.data.LidDataComponent;
import net.lxshh.jammies.common.util.data.LidProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public final class TooltipEvent {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(TooltipEvent::onItemToolTip);
    }

    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.has(JammiesDataComponent.JAR_LID_COMPONENT)) {
            LidDataComponent lidData = stack.get(JammiesDataComponent.JAR_LID_COMPONENT);
            if (lidData != null && !lidData.lidStack().isEmpty()) {
                ItemStack lidStack = lidData.lidStack();

                String translationKey = LidProperties.getTranslationKey(lidStack);

                if (translationKey.equals("jammies.generic.lid")) {
                    translationKey = lidStack.getItem().getDescriptionId();
                }

                event.getToolTip()
                        .add(1, Component.translatable("message.jammies.start.lid")
                                .append(Component.translatable(translationKey)));
            }
        }
    }
}
