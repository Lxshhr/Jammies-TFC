package net.lxshh.jammies.common.util.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public record LidProperties(Ingredient lidItem, float returnChance, String translationKey) {

    public static final Codec<LidProperties> CODEC = RecordCodecBuilder.create(i -> i.group(
            Ingredient.CODEC.fieldOf("lid_item").forGetter(c -> c.lidItem),
            Codec.floatRange(0.0F, 1.0F).fieldOf("return_chance").forGetter(c -> c.returnChance),
            Codec.STRING.optionalFieldOf("translation_key", "item.generic.lid").forGetter(c -> c.translationKey)
    ).apply(i, LidProperties::new));


    public boolean matches(Item item) {
        return lidItem.test(item.getDefaultInstance());
    }

}
