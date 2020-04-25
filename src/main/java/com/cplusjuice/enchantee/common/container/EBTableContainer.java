package com.cplusjuice.enchantee.common.container;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.tentity.EBTableTileEntity;
import com.cplusjuice.enchantee.network.EBTDowngradeMessageHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import static com.cplusjuice.enchantee.common.block.METableBlock.MAIN_SLOT_INDEX;
import static com.cplusjuice.enchantee.util.EnchantmentUtils.ENCH_PROPERTY_ID;
import static com.cplusjuice.enchantee.util.EnchantmentUtils.ENCH_PROPERTY_LVL;

public class EBTableContainer extends EnchanteeContainer {

    private final EBTableTileEntity tileEntity;
    private final InventoryPlayer inventory;
    private final String handlerId;

    public EBTableContainer(InventoryPlayer inventory,
                            EBTableTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        this.inventory  = inventory;

        addSlotToContainer(new Slot(tileEntity, 0, 148, 58));
        addPlayerInventory(inventory);

        handlerId = EBTDowngradeMessageHandler.addHandler(msg -> {
            BlockPos position = tileEntity.getPos();
            if (    position.getX() == msg.getX()
                 && position.getY() == msg.getY()
                 && position.getZ() == msg.getZ()) {

                downgradeItem(MAIN_SLOT_INDEX, msg.getEnchantmentId());
            }
        });
    }

    private boolean canDowngrade(int enchantmentId) {

        tileEntity.refreshInfo(enchantmentId);

        if (!getSlot(MAIN_SLOT_INDEX).getHasStack())
            return false;

        return true;
    }

    private void downgradeItem(int slot, int enchantmentId) {

        if (!canDowngrade(enchantmentId)) {
            EnchanteeMod.logger.warn("Player can't downgrade item");
            return;
        }

        ItemStack item = getSlot(slot).getStack();

        int index = 0;
        int currentLevel = 0;
        NBTTagList currentEnchantments = item.getEnchantmentTagList();
        for (NBTBase tag : currentEnchantments) {

            NBTTagCompound castedTag = (NBTTagCompound) tag;
            if (castedTag.getShort(ENCH_PROPERTY_ID) == enchantmentId) {
                currentLevel = castedTag.getShort(ENCH_PROPERTY_LVL);
                currentEnchantments.removeTag(index);
                break;
            }

            index++;
        }

        Enchantment enchantment = Enchantment.getEnchantmentByID(enchantmentId);
        if (enchantment == null) {
            EnchanteeMod.logger.error("Enchantment with ID " + enchantmentId + " doesn't exists in game. Stopping...");
            return;
        }

        item.addEnchantment(enchantment, currentLevel - 1);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        EBTDowngradeMessageHandler.removeHandler(handlerId);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return tileEntity.isUsableByPlayer(entityPlayer);
    }
}
