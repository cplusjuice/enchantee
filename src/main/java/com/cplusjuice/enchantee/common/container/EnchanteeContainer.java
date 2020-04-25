package com.cplusjuice.enchantee.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class EnchanteeContainer extends Container {

    protected void addPlayerInventory(InventoryPlayer inventory) {

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(inventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStackBuffer = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            itemStackBuffer = itemStack.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {

                if (!mergeItemStack(itemStack, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }

            } else if (!mergeItemStack(itemStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack.getCount() == itemStackBuffer.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack);
        }

        return itemStackBuffer;
    }
}
