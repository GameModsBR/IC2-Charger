package br.com.gamemods.ic2.charger.charger;

import ic2.core.IC2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiCharger extends GuiContainer
{
    private static final ResourceLocation background = new ResourceLocation(IC2.textureDomain, "textures/gui/GUIElectricBlock.png");
    private final TileEntityCharger charger;
    private final String name, armor, level;
    public GuiCharger(InventoryPlayer inventoryPlayer, TileEntityCharger charger)
    {
        super(new ContainerCharger(inventoryPlayer, charger));
        this.charger = charger;
        ySize = 196;

        switch (charger.tier)
        {
            case 1: name = StatCollector.translateToLocal("ic2.blockBatBox"); break;
            case 2: name = StatCollector.translateToLocal("ic2.blockCESU"); break;
            case 3: name = StatCollector.translateToLocal("ic2.blockMFE"); break;
            case 4: name = StatCollector.translateToLocal("ic2.blockMFSU"); break;
            default: name = "";
        }

        armor = StatCollector.translateToLocal("ic2.EUStorage.gui.info.armor");
        level = StatCollector.translateToLocal("ic2.EUStorage.gui.info.level");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        fontRendererObj.drawString(name, (xSize - fontRendererObj.getStringWidth(name)) / 2, 6, 4210752);
        fontRendererObj.drawString(armor, 8, ySize - 126 + 3, 4210752);
        fontRendererObj.drawString(level, 79, 25, 4210752);

        int max = (int)Math.min(charger.energy, charger.maxStorage);
        this.fontRendererObj.drawString(" " + max, 110, 35, 4210752);

        this.fontRendererObj.drawString("/" + (int)charger.maxStorage, 110, 45, 4210752);
        String voltage = StatCollector.translateToLocalFormatted("ic2.charger.max", charger.voltage);
        this.fontRendererObj.drawString(voltage, 85, 60, 4210752);
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
