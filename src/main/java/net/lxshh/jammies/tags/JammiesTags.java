package net.lxshh.jammies.tags;

import net.lxshh.jammies.Jammies;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class JammiesTags {
    public static class Items {

        public static final TagKey<Item> LIDS = tag("lids");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Jammies.MOD_ID, name));
        }
    }
}
