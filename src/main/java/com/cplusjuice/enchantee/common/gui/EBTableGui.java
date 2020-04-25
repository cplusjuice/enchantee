package com.cplusjuice.enchantee.common.gui;

import com.cplusjuice.enchantee.EnchanteeMod;
import com.cplusjuice.enchantee.common.container.EBTableContainer;
import com.cplusjuice.enchantee.common.tentity.EBTableTileEntity;
import com.cplusjuice.enchantee.network.EBTDowngradeMessage;
import com.cplusjuice.enchantee.network.EnchanteeChannel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class EBTableGui extends GuiContainer {

    private final InventoryPlayer inventoryPlayer;
    private final EBTableTileEntity tileEntity;

    private static final int BTN_PREVIOUS_ID  = 0;
    private static final int BTN_NEXT_ID      = 1;
    private static final int BTN_DOWNGRADE_ID = 2;

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            EnchanteeMod.MODID + ":textures/gui/container/mastery_enchantment_table.png");

    private GuiButton downgradeButton;

    public EBTableGui(InventoryPlayer inventoryPlayer,
                      EBTableTileEntity tileEntity) {
        super(new EBTableContainer(inventoryPlayer, tileEntity));
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = tileEntity;
    }

    @Override
    public void initGui() {
        super.initGui();

        downgradeButton = new EnchanteeGuiButton(BTN_DOWNGRADE_ID, guiLeft + 7, guiTop + 63, 132, 16, "reduce");

        addButton(new EnchanteeGuiButton(BTN_PREVIOUS_ID, guiLeft + 7, guiTop + 7, 16, 16, "<"));
        addButton(new EnchanteeGuiButton(BTN_NEXT_ID, guiLeft + 153, guiTop + 7, 16, 16, ">"));
        addButton(downgradeButton);
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
            case BTN_DOWNGRADE_ID:
                EnchanteeChannel.INSTANCE.sendToServer(new EBTDowngradeMessage(tileEntity.getPos().getX(),
                        tileEntity.getPos().getY(), tileEntity.getPos().getZ(), tileEntity.getCurrentId()));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = tileEntity.getCurrentEnchantmentName();

        fontRenderer.drawString(tileName, (xSize / 2 -
                        fontRenderer.getStringWidth(tileName) / 2), 11, 0x404040);

        fontRenderer.drawString("Current: " + tileEntity.getCurrentLevel(), 7, 27, 0x404040);
        downgradeButton.visible = !tileEntity.isEmpty() && tileEntity.isCanDowngrade();
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
