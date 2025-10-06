package net.lxshh.jammies.event;

import net.lxshh.jammies.common.data.JammiesDataComponent;
import net.lxshh.jammies.common.data.LidDataComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public final class TooltipEvent {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(TooltipEvent::onItemToolTip);
    }

    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.has(JammiesDataComponent.JAR_LID_COMPONENT)) {
            LidDataComponent customData = stack.get(JammiesDataComponent.JAR_LID_COMPONENT);
            String lidItem = customData.lidStack().getItem().getDescriptionId();

            // TODO: Add componentTranslatable to lidProperties
            event.getToolTip()
                    .add(Component.translatable("message.jammies.start.lid")
                            .append(Component.translatable(lidItem)));

        }
    }
}
