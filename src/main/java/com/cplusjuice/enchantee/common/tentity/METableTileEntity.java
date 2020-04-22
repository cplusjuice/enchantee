package com.cplusjuice.enchantee.common.tentity;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.cplusjuice.enchantee.common.block.METableBlock.MAIN_SLOT_INDEX;

public class METableTileEntity extends TileEntity implements IInventory {

    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private String customName;
    private static final List<Enchantment> allEnchantments;
    private int selectedEnchantment = 0;
    private int currentLevel = 0;
    private int nextCost = 0;

    private static final String ENCH_PROPERTY_ID  = "id";
    private static final String ENCH_PROPERTY_LVL = "lvl";

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
        refreshCurrentInfo();

        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
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

    public void nextEnchantment() {

        if (selectedEnchantment == allEnchantments.size() - 1)
            selectedEnchantment = 0;
        else selectedEnchantment++;

        refreshCurrentInfo();
    }

    public void previousEnchantment() {

        if (selectedEnchantment == 0)
            selectedEnchantment = allEnchantments.size() - 1;
        else selectedEnchantment--;

        refreshCurrentInfo();
    }

    @SuppressWarnings("deperecation")
    public String getCurrentEnchantmentName() {
        Enchantment enchantment = allEnchantments.get(selectedEnchantment);

        String result = I18n.translateToLocal(enchantment.getName());
        if (enchantment.isCurse()) {
            result = TextFormatting.RED + result;
        }

        return result;
    }

    public void refreshInfo(int enchantmentId) {

        currentLevel = 0;
        for (NBTBase tag : getStackInSlot(MAIN_SLOT_INDEX).getEnchantmentTagList()) {

            NBTTagCompound castedTag = (NBTTagCompound) tag;
            if (castedTag.getShort(ENCH_PROPERTY_ID) == enchantmentId)
                currentLevel = castedTag.getShort(ENCH_PROPERTY_LVL);
        }

        nextCost = (currentLevel + 1) * (currentLevel + 1) + 5;
    }

    private void refreshCurrentInfo() {
        refreshInfo(Enchantment.getEnchantmentID(allEnchantments.get(selectedEnchantment)));
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentId() {
        return Enchantment.getEnchantmentID(allEnchantments.get(selectedEnchantment));
    }

    public int getNextCost() {
        return nextCost;
    }
}
