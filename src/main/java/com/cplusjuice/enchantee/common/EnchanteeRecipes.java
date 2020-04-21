package com.cplusjuice.enchantee.common;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EnchanteeRecipes {

    public static void init() {
        GameRegistry.addSmelting(Blocks.GLASS_PANE, new ItemStack(EnchanteeItems.GLASS_TUBE, 1), 0.3f);
    }
}
