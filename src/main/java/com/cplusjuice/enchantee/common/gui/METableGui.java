package com.cplusjuice.enchantee.common.gui;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.container.METableContainer;
import com.cplusjuice.enchantee.common.tentity.METableTileEntity;
import com.cplusjuice.enchantee.network.EnchanteeChannel;
import com.cplusjuice.enchantee.network.METUpgradeMessage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class METableGui extends GuiContainer {

    private final InventoryPlayer inventoryPlayer;
    private final METableTileEntity tileEntity;

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
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = tileEntity.getCurrentEnchantmentName();

        fontRenderer.drawString(tileName, (xSize / 2 -
                        fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 0x404040);

        fontRenderer.drawString("Current lvl: " + tileEntity.getCurrentLevel(), 7, 27, 0x404040);
        fontRenderer.drawString("Next LVL cost: " + tileEntity.getNextCost(), 7, 31 + fontRenderer.FONT_HEIGHT, 0x00AA00);

        fontRenderer.drawString("upgrade", 11, 67, 0x00AA00);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (mouseIntRect(guiLeft + 7, guiTop + 7, 16, 16, mouseX, mouseY))
            drawTexturedModalRect(guiLeft + 7, guiTop + 7, 0, 187, 16, 16);
        else drawTexturedModalRect(guiLeft + 7, guiTop + 7, 0, 168, 16, 16);

        if (mouseIntRect(guiLeft + 152, guiTop + 7, 16, 16, mouseX, mouseY))
            drawTexturedModalRect(guiLeft + 152, guiTop + 7, 0, 187, 16, 16);
        else drawTexturedModalRect(guiLeft + 152, guiTop + 7, 0, 168, 16, 16);

        if (mouseIntRect(guiLeft + 7, guiTop + 63, 45, 16, mouseX, mouseY))
            drawTexturedModalRect(guiLeft + 7, guiTop + 63, 18, 187, 45, 16);
        else drawTexturedModalRect(guiLeft + 7, guiTop + 63, 18, 168, 45, 16);
    }

    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);

        if (button != 0) return;

        if (mouseIntRect(guiLeft + 152, guiTop + 7, 16, 16, x, y))
            tileEntity.nextEnchantment();

        if (mouseIntRect(guiLeft + 7, guiTop + 7, 16, 16, x, y))
            tileEntity.previousEnchantment();

        if (mouseIntRect(guiLeft + 7, guiTop + 63, 45, 16, x, y))
            EnchanteeChannel.INSTANCE.sendToServer(new METUpgradeMessage(tileEntity.getPos().getX(),
                    tileEntity.getPos().getY(), tileEntity.getPos().getZ(), tileEntity.getCurrentId()));
    }
}
