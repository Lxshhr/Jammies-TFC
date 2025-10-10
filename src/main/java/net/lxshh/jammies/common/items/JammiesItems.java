package net.lxshh.jammies.common.items;

import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.util.Helpers;
import net.lxshh.jammies.Jammies;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.Supplier;

public class JammiesItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Jammies.MOD_ID);

    public static final DeferredItem<Item> ALUMINIUM_LID = register("aluminium_lid");

    public static final DeferredItem<Item> EMPTY_CLAY_JAR = register("empty_clay_jar");
    public static final Map<Food, DeferredItem<Item>> CLAY_PRESERVES = Helpers.mapOf(Food.class, Food::hasJam, food ->
            register("ceramic/jar/" + food.name(), () -> new Item(new Item.Properties().craftRemainder(JammiesItems.EMPTY_CLAY_JAR.asItem()))));

    private static DeferredItem<Item> register(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    private static DeferredItem<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }
}
