package com.cplusjuice.enchantee.network;

public interface MessageHandler<T> {
    void handle(T message);
}
