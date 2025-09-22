package net.lxshh.jammies.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public final class TooltipEvent {

    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(TooltipEvent::onItemToolTip);
    }

    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.has(DataComponents.CUSTOM_DATA)) {
            var customData = stack.get(DataComponents.CUSTOM_DATA);
            CompoundTag tag = customData.copyTag();

            if (tag.contains("lidType")) {
                String lidItem = tag.getString("lidType");
                String lidDisplayName = "item." +  lidItem.replace(":", ".");
                event.getToolTip()
                        .add(Component.translatable("message.jammies.start.lid").withStyle(ChatFormatting.YELLOW)
                                .append(Component.translatable(lidDisplayName)).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
