package net.lxshh.jammies.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lxshh.jammies.common.util.LidProperties;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record LidDataComponent(ItemStack lidStack, float returnChance) {

    public static final Codec<LidDataComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            ItemStack.CODEC.fieldOf("lidstack").forGetter(c -> c.lidStack),
            Codec.FLOAT.fieldOf("return_chance").forGetter(c -> c.returnChance)
    ).apply(i, LidDataComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, LidDataComponent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, c -> c.lidStack,
            ByteBufCodecs.FLOAT, c -> c.returnChance,
            LidDataComponent::new
    );

    public static LidDataComponent of(ItemStack lidStack) {
        float returnChance = 0.0f;
        if (lidStack.isEmpty()) {
            return new LidDataComponent(ItemStack.EMPTY, returnChance);
        }
        returnChance = LidProperties.getReturnChance(lidStack);

        ItemStack newStack = new ItemStack(lidStack.getItem(), 1);
        return new LidDataComponent(newStack, returnChance);
    }

    public static void addTooltipInfo(ItemStack stack, List<Component> tooltips) {
        final @Nullable LidDataComponent component = stack.get(JammiesDataComponent.JAR_LID_COMPONENT);
        if (component != null && !component.lidStack().isEmpty()) {
            ItemStack lidstack = component.lidStack();

            String defaultTranslationKey = LidProperties.getTranslationKey(lidstack);
            float returnChance = LidProperties.getReturnChance(lidstack);

            String translationKey = defaultTranslationKey;

            if (defaultTranslationKey.equals("jammies.generic.lid") || !I18n.exists(defaultTranslationKey)) {
                String itemTranslationKey = lidstack.getDescriptionId();

                if (I18n.exists(itemTranslationKey)) {
                    translationKey = itemTranslationKey;
                }
            }

            tooltips.add(1, Component.translatable("message.jammies.lid.start")
                    .append(Component.translatable(translationKey)));
            tooltips.add(2, Component.translatable("message.jammies.lid.return_chance", String.format("%.0f%%", returnChance * 100)));
        }
    }
}
