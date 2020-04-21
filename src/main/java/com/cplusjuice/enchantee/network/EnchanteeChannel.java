package com.cplusjuice.enchantee.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import static com.cplusjuice.enchantee.EnchanteeMod.MODID;

public class EnchanteeChannel {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
}
