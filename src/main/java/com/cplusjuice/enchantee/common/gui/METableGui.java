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

public class METableGui extends GuiContainer {

    private final InventoryPlayer inventoryPlayer;
    private final METableTileEntity tileEntity;

    private static final int BTN_PREVIOUS_ID = 0;
    private static final int BTN_NEXT_ID     = 1;
    private static final int BTN_UPGRADE_ID  = 2;

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            EnchanteeMod.MODID + ":textures/gui/container/mastery_enchantment_table.png");

    private GuiButton upgradeButton;

    public METableGui(InventoryPlayer inventoryPlayer,
                      METableTileEntity tileEntity) {
        super(new METableContainer(inventoryPlayer, tileEntity));
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = tileEntity;
    }

    @Override
    public void initGui() {
        super.initGui();

        upgradeButton = new EnchanteeGuiButton(BTN_UPGRADE_ID, guiLeft + 7, guiTop + 63, 132, 16, "upgrade");

        addButton(new EnchanteeGuiButton(BTN_PREVIOUS_ID, guiLeft + 7, guiTop + 7, 16, 16, "<"));
        addButton(new EnchanteeGuiButton(BTN_NEXT_ID, guiLeft + 153, guiTop + 7, 16, 16, ">"));
        addButton(upgradeButton);
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
                        fontRenderer.getStringWidth(tileName) / 2), 11, 0x404040);

        fontRenderer.drawString("Current: " + tileEntity.getCurrentLevel(), 7, 27, 0x404040);

        boolean canUpgrade = tileEntity.getNextCost() <= inventoryPlayer.player.experienceLevel;

        fontRenderer.drawString("Next cost: " + tileEntity.getNextCost(), 7, 31 + fontRenderer.FONT_HEIGHT,
                canUpgrade ? 0x00AA00 : 0xAA0000);

        upgradeButton.visible = !tileEntity.isEmpty() && canUpgrade;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
