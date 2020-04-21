package com.cplusjuice.enchantee.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class METUpgradeMessage implements IMessage {

    private int x, y, z, enchantmentId;

    public METUpgradeMessage() {
    }

    public METUpgradeMessage(int x, int y, int z, int enchantmentId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.enchantmentId = enchantmentId;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(enchantmentId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.enchantmentId = buf.readInt();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getEnchantmentId() {
        return enchantmentId;
    }

    public void setEnchantmentId(int enchantmentId) {
        this.enchantmentId = enchantmentId;
    }
}
