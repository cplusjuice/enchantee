package com.cplusjuice.enchantee.common.gui;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.container.METableContainer;
import com.cplusjuice.enchantee.common.tentity.METableTileEntity;
import com.cplusjuice.enchantee.network.EnchanteeChannel;
import com.cplusjuice.enchantee.network.METUpgradeMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class METableGui extends GuiContainer {

    private final InventoryPlayer inventoryPlayer;
    private final METableTileEntity tileEntity;

    private static final int BTN_PREVIOUS_ID = 0;
    private static final int BTN_NEXT_ID     = 1;
    private static final int BTN_UPGRADE_ID  = 2;

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            EnchanteeMod.MODID + ":textures/gui/container/mastery_enchantment_table.png");

    public METableGui(InventoryPlayer inventoryPlayer,
                      METableTileEntity tileEntity) {
        super(new METableContainer(inventoryPlayer, tileEntity));
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = tileEntity;
    }

    private boolean mouseIntRect(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public void initGui() {
        super.initGui();

        addButton(new EnchanteeGuiButton(BTN_PREVIOUS_ID, guiLeft + 7, guiTop + 7, 16, 16, "<"));
        addButton(new EnchanteeGuiButton(BTN_NEXT_ID, guiLeft + 153, guiTop + 7, 16, 16, ">"));
        addButton(new EnchanteeGuiButton(BTN_UPGRADE_ID, guiLeft + 7, guiTop + 63, 132, 16, "upgrade"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {

        switch (button.id) {
            case BTN_PREVIOUS_ID:
                tileEntity.previousEnchantment();
                break;
            case BTN_NEXT_ID:
                tileEntity.nextEnchantment();
                break;
            case BTN_UPGRADE_ID:
                EnchanteeChannel.INSTANCE.sendToServer(new METUpgradeMessage(tileEntity.getPos().getX(),
                        tileEntity.getPos().getY(), tileEntity.getPos().getZ(), tileEntity.getCurrentId()));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = tileEntity.getCurrentEnchantmentName();

        fontRenderer.drawString(tileName, (xSize / 2 -
                        fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 0x404040);

        fontRenderer.drawString("Current lvl: " + tileEntity.getCurrentLevel(), 7, 27, 0x404040);
        fontRenderer.drawString("Next LVL cost: " + tileEntity.getNextCost(), 7, 31 + fontRenderer.FONT_HEIGHT, 0x00AA00);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
