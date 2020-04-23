package com.cplusjuice.enchantee.common;

import com.cplusjuice.enchantee.common.item.ChargedXpHolder;
import com.cplusjuice.enchantee.common.item.XpHolder;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class EnchanteeItems {

    private static final List<Item> items = new ArrayList<>();

    public static final Item BLAZED_DIAMOND    = new EnchanteeItem("blazed_diamond");
    public static final Item GLASS_TUBE        = new EnchanteeItem("glass_tube");
    public static final Item BLAZED_GLASS_TUBE = new EnchanteeItem("blazed_glass_tube");
    public static final Item XP_HOLDER         = new XpHolder();
    public static final Item CHARGED_XP_HOLDER = new ChargedXpHolder();

    public static void add(Item item) {
        items.add(item);
    }

    public static Item[] getItems() {
        return items.toArray(new Item[0]);
    }
}
