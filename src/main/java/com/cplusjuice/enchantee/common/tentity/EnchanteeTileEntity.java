package com.cplusjuice.enchantee.common.tentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public abstract class EnchanteeTileEntity extends TileEntity implements IInventory {

    protected NonNullList<ItemStack> inventory;
    protected int stackLimit = 1;
    private String customName;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        ItemStackHelper.loadAllItems(compound, inventory);
        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        ItemStackHelper.saveAllItems(compound, inventory);
        if (compound.hasKey("CustomName", 8)) {
            compound.setString("CustomName", this.customName);
        }

        return compound;
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {

        for (ItemStack itemStack : inventory) {
            if (!itemStack.isEmpty())
                return false;
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int number) {
        return ItemStackHelper.getAndSplit(inventory, index, number);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        inventory.set(index, itemStack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer) {
    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return true;
    }

    @Override
    public int getField(int i) {
        return 0;
    }

    @Override
    public void setField(int index, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public String getName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Override
    public boolean hasCustomName() {
        return customName != null && !customName.isEmpty();
    }
}
