package com.cplusjuice.enchantee.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class METUpgradeMessageHandler implements IMessageHandler<METUpgradeMessage, IMessage> {

    private static final Map<String, MessageHandler<METUpgradeMessage>> handlers = new HashMap<>();

    @Override
    public METUpgradeMessage onMessage(METUpgradeMessage metUpgradeMessage, MessageContext messageContext) {

        for (MessageHandler<METUpgradeMessage> handler : handlers.values()) {
            handler.handle(metUpgradeMessage);
        }

        return null;
    }

    public static String addHandler(MessageHandler<METUpgradeMessage> handler) {
        String id = UUID.randomUUID().toString();
        handlers.put(id, handler);

        return id;
    }

    public static void removeHandler(String handlerId) {
        handlers.remove(handlerId);
    }
}
