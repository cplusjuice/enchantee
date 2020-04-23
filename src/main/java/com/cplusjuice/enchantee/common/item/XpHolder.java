package com.cplusjuice.enchantee.common.item;

import com.cplusjuice.enchantee.common.EnchanteeItem;
import com.cplusjuice.enchantee.common.EnchanteeItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.cplusjuice.enchantee.common.EnchanteeItems.CHARGED_XP_HOLDER;

public class XpHolder extends EnchanteeItem {

    public XpHolder() {
        super("xp_holder");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        if (player.experienceLevel < 15) {
            // TODO: Play sound or something
        } else {
            player.addExperienceLevel(-15);
            ItemStack currentItem = player.inventory.getCurrentItem();

            if (currentItem.getCount() > 1) {
                currentItem.setCount(currentItem.getCount() - 1);
            } else {
                player.inventory.removeStackFromSlot(player.inventory.currentItem);
            }

            if (!player.inventory.addItemStackToInventory(new ItemStack(CHARGED_XP_HOLDER))) {
                world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ,
                        new ItemStack(CHARGED_XP_HOLDER)));
            }
        }

        return super.onItemRightClick(world, player, hand);
    }
}
