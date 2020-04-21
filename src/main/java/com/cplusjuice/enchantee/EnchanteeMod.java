package com.cplusjuice.enchantee;

import com.cplusjuice.enchantee.common.EnchanteeItems;
import com.cplusjuice.enchantee.common.EnchanteeRecipes;
import com.cplusjuice.enchantee.handle.RegistryHandler;
import com.cplusjuice.enchantee.proxy.Proxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = EnchanteeMod.MODID, name = EnchanteeMod.NAME, version = EnchanteeMod.VERSION)
public class EnchanteeMod {

    public static final String MODID = "enchantee";
    public static final String NAME = "Enchantee";
    public static final String VERSION = "0.0.1";
    public static final String SUPPORTED_VERSIONS = "[1.12.2]";
    public static final String PROXY_CLASS = "com.cplusjuice.enchantee.proxy.Proxy";
    public static final String CLIENT_PROXY_CLASS = "com.cplusjuice.enchantee.proxy.ClientProxy";
    public static Logger logger;

    @Instance
    public static EnchanteeMod INSTANCE;

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = PROXY_CLASS)
    public static Proxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EnchanteeRecipes.init();
        proxy.init();
    }
}
