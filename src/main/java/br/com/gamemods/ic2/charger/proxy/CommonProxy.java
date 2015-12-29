package br.com.gamemods.ic2.charger.proxy;

import br.com.gamemods.ic2.charger.ChargerMod;
import br.com.gamemods.ic2.charger.GuiHandler;
import br.com.gamemods.ic2.charger.charger.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public BlockCharger blockCharger;

    public void registerBlocks()
    {
        GameRegistry.registerBlock(blockCharger = new BlockCharger(), ItemCharger.class, "charger");
    }

    public void registerTiles()
    {
        GameRegistry.registerTileEntity(TileEntityChargerLV.class, "ic2charger:LV-Charger");
        GameRegistry.registerTileEntity(TileEntityChargerMV.class, "ic2charger:MV-Charger");
        GameRegistry.registerTileEntity(TileEntityChargerHV.class, "ic2charger:HV-Charger");
        GameRegistry.registerTileEntity(TileEntityChargerEV.class, "ic2charger:EV-Charger");
    }

    public void registerNetwork(ChargerMod mod)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(mod, new GuiHandler());
    }
}
