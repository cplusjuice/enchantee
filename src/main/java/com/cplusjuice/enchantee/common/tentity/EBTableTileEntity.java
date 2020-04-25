package com.cplusjuice.enchantee.common.tentity;

import com.cplusjuice.enchantee.util.EnchantmentUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

import static com.cplusjuice.enchantee.common.block.METableBlock.MAIN_SLOT_INDEX;
import static com.cplusjuice.enchantee.util.EnchantmentUtils.ENCH_PROPERTY_ID;
import static com.cplusjuice.enchantee.util.EnchantmentUtils.ENCH_PROPERTY_LVL;

public class EBTableTileEntity extends EnchanteeTileEntity {

    private static final List<Enchantment> allEnchantments = EnchantmentUtils.getAllEnchantments();
    private int selectedEnchantment = 0;
    private int currentLevel = 0;
    private boolean canDowngrade = false;

    public EBTableTileEntity() {
        this.inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        inventory.set(index, itemStack);
        refreshCurrentInfo();

        markDirty();
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

        canDowngrade = currentLevel > 0;
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

    public boolean isCanDowngrade() {
        return canDowngrade;
    }
}
