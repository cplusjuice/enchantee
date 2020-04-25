package com.cplusjuice.enchantee.common;

import com.cplusjuice.enchantee.common.block.EBTableBlock;
import com.cplusjuice.enchantee.common.block.METableBlock;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class EnchanteeBlocks {

    private static final List<Block> blocks = new ArrayList<>();

    public static final Block MASTERY_ENCHANTMENT_TABLE = new METableBlock();
    public static final Block ENCHANTMENT_BREAKING_TABLE = new EBTableBlock();

    public static void add(EnchanteeBlock block) {
        blocks.add(block);
    }

    public static Block[] getItems() {
        return blocks.toArray(new Block[0]);
    }
}
