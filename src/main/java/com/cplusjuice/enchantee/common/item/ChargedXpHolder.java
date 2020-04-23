package com.cplusjuice.enchantee.common.item;

import com.cplusjuice.enchantee.common.EnchanteeItem;
import com.cplusjuice.enchantee.common.EnchanteeItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import static net.minecraft.util.math.RayTraceResult.Type.ENTITY;

public class ChargedXpHolder extends EnchanteeItem {

    public ChargedXpHolder() {
        super("charged_xp_holder");
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult rayTraceResult = Minecraft.getMinecraft().objectMouseOver;

        if (rayTraceResult != null) {

            if (rayTraceResult.typeOfHit.equals(ENTITY)) {
                ItemStack currentItem = player.inventory.getCurrentItem();

                if (currentItem.getCount() > 1) {
                    currentItem.setCount(currentItem.getCount() - 1);
                } else {
                    player.inventory.removeStackFromSlot(player.inventory.currentItem);
                }

                player.inventory.addItemStackToInventory(new ItemStack(EnchanteeItems.XP_HOLDER));

                Entity entity = rayTraceResult.entityHit;
                Explosion explosion = new Explosion(world, player, entity.posX, entity.posY,
                        entity.posZ, 2.5f, false, true);

                explosion.doExplosionA();
                world.playSound(player, new BlockPos(entity.posX, entity.posY, entity.posZ),
                        SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                explosion.doExplosionB(true);
            }
        }

        return super.onItemRightClick(world, player, hand);
    }
}
