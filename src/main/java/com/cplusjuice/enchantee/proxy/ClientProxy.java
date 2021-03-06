package com.cplusjuice.enchantee.proxy;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.handle.GuiHandler;
import com.cplusjuice.enchantee.network.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static java.util.Objects.requireNonNull;

public class ClientProxy extends Proxy {

    @Override
    public void preInit() {
        EnchanteeChannel.INSTANCE.registerMessage(
                METUpgradeMessageHandler.class, METUpgradeMessage.class, 0, Side.SERVER);
        EnchanteeChannel.INSTANCE.registerMessage(
                EBTDowngradeMessageHandler.class, EBTDowngradeMessage.class, 1, Side.SERVER);
    }

    @Override
    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(EnchanteeMod.INSTANCE, new GuiHandler());
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(
                        requireNonNull(item.getRegistryName()), id));
    }
}
