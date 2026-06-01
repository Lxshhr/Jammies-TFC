package net.lxshh.jammies.registry;

import net.lxshh.jammies.Jammies;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class JammiesTags {
    public static class Items {
        public static final TagKey<Item> LIDS = TagKey.create(Registries.ITEM, Jammies.loc("lids"));
    }
}
