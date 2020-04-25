package com.cplusjuice.enchantee.handle;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.block.EBTableBlock;
import com.cplusjuice.enchantee.common.block.METableBlock;
import com.cplusjuice.enchantee.common.tentity.EBTableTileEntity;
import com.cplusjuice.enchantee.common.tentity.METableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(METableTileEntity.class,
                new ResourceLocation(EnchanteeMod.MODID + ":" + METableBlock.NAME));
        GameRegistry.registerTileEntity(EBTableTileEntity.class,
                new ResourceLocation(EnchanteeMod.MODID + ":" + EBTableBlock.NAME));
    }
}
