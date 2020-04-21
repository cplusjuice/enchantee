package com.cplusjuice.enchantee.common.block;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.EnchanteeBlock;
import com.cplusjuice.enchantee.common.tentity.METableTileEntity;
import com.cplusjuice.enchantee.handle.GuiHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class METableBlock extends EnchanteeBlock implements ITileEntityProvider {

    public static final String NAME = "mastery_enchantment_table";
    public static final int MAIN_SLOT_INDEX = 0;

    public METableBlock() {
        super(NAME, Material.IRON);
        setSoundType(SoundType.ANVIL);
    }

    @Override
    public boolean onBlockActivated(World world,
                                    BlockPos blockPos,
                                    IBlockState iBlockState,
                                    EntityPlayer entityPlayer,
                                    EnumHand enumHand,
                                    EnumFacing enumFacing,
                                    float x, float y, float z) {

        if (!world.isRemote) {
            entityPlayer.openGui(EnchanteeMod.INSTANCE, GuiHandler.MASTERY_ENCHANTMENT_TABLE_ID,
                    world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        return super.onBlockActivated(world, blockPos, iBlockState,
                entityPlayer, enumHand, enumFacing, x, y, z);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new METableTileEntity();
    }
}
