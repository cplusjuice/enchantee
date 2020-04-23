package com.cplusjuice.enchantee.common;

import com.cplusjuice.enchantee.EnchanteeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchanteeItem extends Item implements Model {

    public EnchanteeItem(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        //TODO: Replace with mod creative tab
        setCreativeTab(CreativeTabs.MATERIALS);

        EnchanteeItems.add(this);
    }

    @Override
    public void register() {
        EnchanteeMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
