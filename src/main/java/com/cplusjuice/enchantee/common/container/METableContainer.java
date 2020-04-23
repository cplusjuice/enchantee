package com.cplusjuice.enchantee.common.container;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.tentity.METableTileEntity;
import com.cplusjuice.enchantee.network.EnchanteeMessageHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import static com.cplusjuice.enchantee.common.block.METableBlock.MAIN_SLOT_INDEX;

public class METableContainer extends Container {

    private final METableTileEntity tileEntity;
    private final InventoryPlayer inventory;
    private final String handlerId;

    private static final String ENCH_PROPERTY_ID  = "id";
    private static final String ENCH_PROPERTY_LVL = "lvl";

    public METableContainer(InventoryPlayer inventory,
                            METableTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        this.inventory  = inventory;

        addSlotToContainer(new Slot(tileEntity, 0, 148, 58));

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(inventory, x, 8 + x*18, 142));
        }

        handlerId = EnchanteeMessageHandler.addHandler(msg -> {
            BlockPos position = tileEntity.getPos();
            if (    position.getX() == msg.getX()
                 && position.getY() == msg.getY()
                 && position.getZ() == msg.getZ()) {

                upgradeItem(MAIN_SLOT_INDEX, msg.getEnchantmentId());
            }
        });
    }

    private boolean canUpgrade(int enchantmentId) {

        tileEntity.refreshInfo(enchantmentId);

        if (!getSlot(MAIN_SLOT_INDEX).getHasStack())
            return false;

        if (tileEntity.getNextCost() > inventory.player.experienceLevel)
            return false;

        return true;
    }

    private void upgradeItem(int slot, int enchantmentId) {

        if (!canUpgrade(enchantmentId)) {
            EnchanteeMod.logger.warn("Player can't upgrade item");
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

        inventory.player.addExperienceLevel(-tileEntity.getNextCost());
        item.addEnchantment(enchantment, currentLevel + 1);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        EnchanteeMessageHandler.removeHandler(handlerId);
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

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return tileEntity.isUsableByPlayer(entityPlayer);
    }
}
