package net.lxshh.jammies.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record LidDataComponent(ItemStack lidStack) {

    public static final Codec<LidDataComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
            ItemStack.CODEC.fieldOf("lidstack").forGetter(c -> c.lidStack)
    ).apply(i, LidDataComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, LidDataComponent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, c -> c.lidStack,
            LidDataComponent::new
    );

    public static LidDataComponent of(ItemStack lidStack) {
        return new LidDataComponent(lidStack.copy());
    }
}
