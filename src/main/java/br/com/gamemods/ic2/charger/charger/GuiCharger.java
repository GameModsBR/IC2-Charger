package br.com.gamemods.ic2.charger.charger;

import ic2.core.IC2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCharger extends GuiContainer
{
    private static final ResourceLocation background = new ResourceLocation(IC2.textureDomain, "textures/gui/GUIElectricBlock.png");
    private final TileEntityCharger charger;
    public GuiCharger(InventoryPlayer inventoryPlayer, TileEntityCharger charger)
    {
        super(new ContainerCharger(inventoryPlayer, charger));
        this.charger = charger;
        ySize = 196;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1f,1f,1f,1f);
        mc.getTextureManager().bindTexture(background);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        if(charger.energy > 0.0D)
        {
            int width = (int)(24.0F * charger.getChargeLevel());
            drawTexturedModalRect(x + 79, y + 34, 176, 14, width + 1, 16);
        }
    }
}
