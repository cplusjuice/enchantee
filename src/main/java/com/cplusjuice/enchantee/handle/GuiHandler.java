package com.cplusjuice.enchantee.handle;

import com.cplusjuice.enchantee.common.container.EBTableContainer;
import com.cplusjuice.enchantee.common.container.METableContainer;
import com.cplusjuice.enchantee.common.gui.EBTableGui;
import com.cplusjuice.enchantee.common.gui.METableGui;
import com.cplusjuice.enchantee.common.tentity.EBTableTileEntity;
import com.cplusjuice.enchantee.common.tentity.METableTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int MASTERY_ENCHANTMENT_TABLE_ID  = 0;
    public static final int ENCHANTMENT_BREAKING_TABLE_ID = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {

        switch (id) {
            case MASTERY_ENCHANTMENT_TABLE_ID:
                return new METableContainer(entityPlayer.inventory,
                        (METableTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
            case ENCHANTMENT_BREAKING_TABLE_ID:
                return new EBTableContainer(entityPlayer.inventory,
                        (EBTableTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z) {

        switch (id) {
            case MASTERY_ENCHANTMENT_TABLE_ID:
                return new METableGui(entityPlayer.inventory,
                        (METableTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
            case ENCHANTMENT_BREAKING_TABLE_ID:
                return new EBTableGui(entityPlayer.inventory,
                        (EBTableTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }

        return null;
    }
}
