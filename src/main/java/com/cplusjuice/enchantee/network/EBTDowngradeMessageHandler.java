package com.cplusjuice.enchantee.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EBTDowngradeMessageHandler implements IMessageHandler<EBTDowngradeMessage, IMessage> {

    private static final Map<String, MessageHandler<EBTDowngradeMessage>> handlers = new HashMap<>();

    @Override
    public METUpgradeMessage onMessage(EBTDowngradeMessage metUpgradeMessage, MessageContext messageContext) {

        for (MessageHandler<EBTDowngradeMessage> handler : handlers.values()) {
            handler.handle(metUpgradeMessage);
        }

        return null;
    }

    public static String addHandler(MessageHandler<EBTDowngradeMessage> handler) {
        String id = UUID.randomUUID().toString();
        handlers.put(id, handler);

        return id;
    }

    public static void removeHandler(String handlerId) {
        handlers.remove(handlerId);
    }
}
