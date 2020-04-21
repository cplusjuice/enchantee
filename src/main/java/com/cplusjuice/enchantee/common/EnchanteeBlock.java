package com.cplusjuice.enchantee.common;

import com.cplusjuice.enchantee.EnchanteeMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import static java.util.Objects.requireNonNull;

public class EnchanteeBlock extends Block implements Model {

    public EnchanteeBlock(String name, Material material) {
        super(material);

        setUnlocalizedName(name);
        setRegistryName(name);
        //TODO: Replace with mod creative tab
        setCreativeTab(CreativeTabs.MATERIALS);

        EnchanteeBlocks.add(this);
        EnchanteeItems.add(new ItemBlock(this).setRegistryName(requireNonNull(this.getRegistryName())));
    }

    @Override
    public void register() {
        EnchanteeMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
