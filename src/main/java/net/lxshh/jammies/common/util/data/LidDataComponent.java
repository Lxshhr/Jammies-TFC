package net.lxshh.jammies.common.util.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

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

    //Todo add constructor to limit itemStack to 1

    public static LidDataComponent of(ItemStack lidStack) {
        float returnChance = 0.0f;
        if (lidStack.isEmpty()) {
            return new LidDataComponent(ItemStack.EMPTY, returnChance);
        }
        returnChance = LidProperties.getReturnChance(lidStack);
        return new LidDataComponent(lidStack.copy(), returnChance);
    }
}
