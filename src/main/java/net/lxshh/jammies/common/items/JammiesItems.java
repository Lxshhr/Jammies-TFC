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

    private static DeferredItem<Item> register(String name) {
        return ITEMS.registerSimpleItem(name);
    }
}
