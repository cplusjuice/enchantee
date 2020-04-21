package com.cplusjuice.enchantee.handle;

import com.cplusjuice.enchantee.common.EnchanteeBlocks;
import com.cplusjuice.enchantee.common.EnchanteeItems;
import com.cplusjuice.enchantee.common.Model;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(EnchanteeItems.getItems());
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        TileEntityHandler.registerTileEntities();
        event.getRegistry().registerAll(EnchanteeBlocks.getItems());
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {

        for (Item item : EnchanteeItems.getItems()) {
            if (item instanceof Model) {
                ((Model) item).register();
            }
        }

        for (Block block : EnchanteeBlocks.getItems()) {
            if (block instanceof Model) {
                ((Model) block).register();
            }
        }
    }
}
