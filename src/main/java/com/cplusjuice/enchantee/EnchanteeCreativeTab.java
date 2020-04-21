package com.cplusjuice.enchantee;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EnchanteeCreativeTab extends CreativeTabs {

    public EnchanteeCreativeTab() {
        super(EnchanteeMod.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return null;
    }
}
