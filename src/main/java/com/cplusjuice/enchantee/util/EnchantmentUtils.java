package com.cplusjuice.enchantee.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EnchantmentUtils {

    public static final String ENCH_PROPERTY_ID  = "id";
    public static final String ENCH_PROPERTY_LVL = "lvl";

    private static final List<Enchantment> allEnchantments;

    static {
        allEnchantments = new ArrayList<>();
        Field[] enchantmentFields = Enchantments.class.getFields();

        for (Field field : enchantmentFields) {
            if (field.getType().equals(Enchantment.class)) {
                try {
                    allEnchantments.add((Enchantment) field.get(Enchantment.class));
                } catch (IllegalAccessException | NullPointerException | ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Enchantment> getAllEnchantments() {
        return allEnchantments;
    }
}
